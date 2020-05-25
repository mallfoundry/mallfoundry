package org.mallfoundry.tracking.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.tracking.InternalTracker;
import org.mallfoundry.tracking.InternalTrackingEvent;
import org.mallfoundry.shipping.Tracker;
import org.mallfoundry.shipping.TrackingEvent;
import org.mallfoundry.tracking.TrackerProvider;
import org.mallfoundry.shipping.TrackingStatus;
import org.mallfoundry.util.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class KdniaoTrackerProvider implements TrackerProvider {

//    private static final String trackUrl = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
//    private static final String trackUrl = "http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";

    private static final Map<String, CarrierCode> shipperCodeCarrierCodeMapping = new HashMap<>();

    private static final Map<CarrierCode, String> carrierCodeShipperCodeMapping = new HashMap<>();

    private static final Map<String, TrackingStatus> stateTrackingStatusMapping = new HashMap<>() {
        {
            this.put("2", TrackingStatus.IN_TRANSIT);
            this.put("3", TrackingStatus.DELIVERED);
            this.put("4", TrackingStatus.FAILURE);
        }
    };

    static {
        initializeCodeMapping();
    }

    private final String apiKey;
    private final String eBusinessId;
    private final String trackUrl;

    public KdniaoTrackerProvider(String url, String apiKey, String eBusinessId) {
        this.apiKey = apiKey;
        this.eBusinessId = eBusinessId;
        this.trackUrl = resolveUrl(url, "/Ebusiness/EbusinessOrderHandle.aspx");
    }

    private String resolveUrl(String url, String path) {
        return UriComponentsBuilder.fromHttpUrl(url).path(path).toUriString();
    }

    @Override
    public Tracker getTracker(CarrierCode carrier, String trackingNumber) {
        String requestData = "{'OrderCode':'','ShipperCode':'" + shipperCode(carrier) +
                "','LogisticCode':'" + trackingNumber + "'}";
        var restTemplate = new RestTemplate();
        var params = new LinkedMultiValueMap<String, String>();
        params.add("RequestData", URLEncoder.encode(requestData, StandardCharsets.UTF_8));
        params.add("EBusinessID", this.eBusinessId);
        params.add("RequestType", "1002");
        params.add("DataType", "2");
        params.add("DataSign", Base64Utils.encodeToString(DigestUtils.md5Hex(requestData + this.apiKey).getBytes()));
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var requestBody = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        var responseString = restTemplate.postForObject(this.trackUrl, requestBody, String.class);
        var trackResponse = JsonUtils.parse(responseString, GetTrackerResponse.class);
        return trackResponse.toTracker();
    }

    private static void initializeCodeMapping() {
        shipperCodeCarrierCodeMapping.put("SF", CarrierCode.SF);
        shipperCodeCarrierCodeMapping.put("YTO", CarrierCode.YTO);
        shipperCodeCarrierCodeMapping.put("STO", CarrierCode.STO);

        for (var shipperCodeCarrierCode : shipperCodeCarrierCodeMapping.entrySet()) {
            carrierCodeShipperCodeMapping.put(shipperCodeCarrierCode.getValue(), shipperCodeCarrierCode.getKey());
        }
    }

    private static CarrierCode carrierCode(String shipperCode) {
        return shipperCodeCarrierCodeMapping.get(shipperCode);
    }

    private static String shipperCode(CarrierCode carrierCode) {
        return carrierCodeShipperCodeMapping.get(carrierCode);
    }

    private static TrackingStatus trackingStatus(String state) {
        var status = stateTrackingStatusMapping.get(state);
        return Objects.isNull(status) ? TrackingStatus.UNKNOWN : status;
    }

    @Getter
    @Setter
    static class GetTrackerResponse {

        @JsonProperty("EBusinessID")
        private String eBusinessId;
        @JsonProperty("OrderCode")
        private String orderCode;
        @JsonProperty("ShipperCode")
        private String shipperCode;
        @JsonProperty("LogisticCode")
        private String logisticCode;
        @JsonProperty("Success")
        private boolean success;
        @JsonProperty("Reason")
        private String reason;
        @JsonProperty("State")
        private String state;

        @JsonProperty("Traces")
        private List<Trace> traces;

        @Getter
        @Setter
        static class Trace {
            @JsonProperty("AcceptTime")
            private String acceptTime;
            @JsonProperty("AcceptStation")
            private String acceptStation;
            @JsonProperty("Remark")
            private String remark;

            public TrackingEvent toEvent() {
                var event = new InternalTrackingEvent();
                try {
                    event.setOccurredTime(DateUtils.parseDate(this.acceptTime, "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                event.setMessage(this.acceptStation);
                return event;
            }
        }


        public Tracker toTracker() {
            var tracker = new InternalTracker();
            tracker.setCarrierCode(carrierCode(this.shipperCode));
            tracker.setTrackingNumber(this.getLogisticCode());
            tracker.setTrackingStatus(trackingStatus(this.state));

            if (CollectionUtils.isNotEmpty(this.traces)) {
                tracker.setEvents(this.traces.stream()
                        .map(Trace::toEvent)
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList()));
            }
            return tracker;
        }
    }
}

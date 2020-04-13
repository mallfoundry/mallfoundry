package com.mallfoundry.tracking.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.carrier.CarrierCode;
import com.mallfoundry.tracking.InternalTracker;
import com.mallfoundry.tracking.InternalTrackingEvent;
import com.mallfoundry.tracking.Tracker;
import com.mallfoundry.tracking.TrackingEvent;
import com.mallfoundry.tracking.TrackingProvider;
import com.mallfoundry.tracking.TrackingStatus;
import com.mallfoundry.util.JsonUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class KdniaoTrackingProvider implements TrackingProvider {

    private static final String trackUrl = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
//    private static final String trackUrl = "http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";

    private static final Map<String, CarrierCode> shipperCodeCarrierCodeMapping = new HashMap<>() {
        {
            this.put("SF", CarrierCode.SF);
            this.put("YTO", CarrierCode.YTO);
            this.put("STO", CarrierCode.STO);
        }
    };

    private static CarrierCode carrierCode(String shipperCode) {
        return shipperCodeCarrierCodeMapping.get(shipperCode);
    }

    private static final Map<String, TrackingStatus> stateTrackingStatusMapping = new HashMap<>() {
        {
            this.put("2", TrackingStatus.TRANSIT);
            this.put("3", TrackingStatus.DELIVERED);
            this.put("4", TrackingStatus.FAILURE);
        }
    };

    private static TrackingStatus trackingStatus(String state) {
        var status = stateTrackingStatusMapping.get(state);
        return Objects.isNull(status) ? TrackingStatus.UNKNOWN : status;
    }

    @Override
    public Tracker getTracking(String carrier, String trackingNumber) {
        String requestData = "{'OrderCode':'','ShipperCode':'" + StringUtils.upperCase(carrier) + "','LogisticCode':'" + trackingNumber + "'}";
//        String eBusinessID = "test1632631";
//        String apiKey = "a341bb71-1c29-4600-b2cf-fbb5723dce4d";
        String eBusinessID = "1632631";
        String apiKey = "03613fea-82ff-4023-a816-a034b8f453b8";

        var restTemplate = new RestTemplate();
        var params = new LinkedMultiValueMap<String, String>();
        params.add("RequestData", URLEncoder.encode(requestData, StandardCharsets.UTF_8));
        params.add("EBusinessID", eBusinessID);
        params.add("RequestType", "1002");
        params.add("DataType", "2");
        params.add("DataSign", Base64Utils.encodeToString(DigestUtils.md5Hex(requestData + apiKey).getBytes()));
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        var requestBody = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        var responseString = restTemplate.postForObject(trackUrl, requestBody, String.class);
        var trackResponse = JsonUtils.parse(responseString, GetTrackerResponse.class);
        return trackResponse.toTracker();
    }

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

        public String geteBusinessId() {
            return eBusinessId;
        }

        public void seteBusinessId(String eBusinessId) {
            this.eBusinessId = eBusinessId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getShipperCode() {
            return shipperCode;
        }

        public void setShipperCode(String shipperCode) {
            this.shipperCode = shipperCode;
        }

        public String getLogisticCode() {
            return logisticCode;
        }

        public void setLogisticCode(String logisticCode) {
            this.logisticCode = logisticCode;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<Trace> getTraces() {
            return traces;
        }

        public void setTraces(List<Trace> traces) {
            this.traces = traces;
        }

        static class Trace {
            @JsonProperty("AcceptTime")
            private String acceptTime;
            @JsonProperty("AcceptStation")
            private String acceptStation;
            @JsonProperty("Remark")
            private String remark;

            public String getAcceptTime() {
                return acceptTime;
            }

            public void setAcceptTime(String acceptTime) {
                this.acceptTime = acceptTime;
            }

            public String getAcceptStation() {
                return acceptStation;
            }

            public void setAcceptStation(String acceptStation) {
                this.acceptStation = acceptStation;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

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
                tracker.setEvents(this.traces.stream().map(Trace::toEvent).collect(Collectors.toList()));
            }
            return tracker;
        }
    }
}

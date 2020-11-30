/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.shipping.tracking.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.tracking.Track;
import org.mallfoundry.shipping.tracking.TrackingEvent;
import org.mallfoundry.shipping.tracking.TrackingStatus;
import org.mallfoundry.shipping.tracking.repository.jpa.InternalTrack;
import org.mallfoundry.shipping.tracking.DefaultTrackingEvent;
import org.mallfoundry.shipping.tracking.TrackProvider;
import org.mallfoundry.util.JsonUtils;
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

public class KdniaoTrackProvider implements TrackProvider {

//    private static final String trackUrl = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
//    private static final String trackUrl = "http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";

    private static final Map<String, CarrierCode> SHIPPER_CODE_CARRIER_CODE_MAPPING = new HashMap<>();

    private static final Map<CarrierCode, String> CARRIER_CODE_SHIPPER_CODE_MAPPING = new HashMap<>();

    private static final Map<String, TrackingStatus> STATE_TRACKING_STATUS_MAPPING = new HashMap<>() {
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

    public KdniaoTrackProvider(String url, String apiKey, String eBusinessId) {
        this.apiKey = apiKey;
        this.eBusinessId = eBusinessId;
        this.trackUrl = resolveUrl(url, "/Ebusiness/EbusinessOrderHandle.aspx");
    }

    private String resolveUrl(String url, String path) {
        return UriComponentsBuilder.fromHttpUrl(url).path(path).toUriString();
    }

    @Override
    public Track getTrack(CarrierCode carrier, String trackingNumber) {
        String requestData = "{'OrderCode':'','ShipperCode':'" + shipperCode(carrier) + "','LogisticCode':'" + trackingNumber + "'}";
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
        SHIPPER_CODE_CARRIER_CODE_MAPPING.put("SF", CarrierCode.SF);
        SHIPPER_CODE_CARRIER_CODE_MAPPING.put("YTO", CarrierCode.YTO);
        SHIPPER_CODE_CARRIER_CODE_MAPPING.put("STO", CarrierCode.STO);

        for (var shipperCodeCarrierCode : SHIPPER_CODE_CARRIER_CODE_MAPPING.entrySet()) {
            CARRIER_CODE_SHIPPER_CODE_MAPPING.put(shipperCodeCarrierCode.getValue(), shipperCodeCarrierCode.getKey());
        }
    }

    private static CarrierCode carrierCode(String shipperCode) {
        return SHIPPER_CODE_CARRIER_CODE_MAPPING.get(shipperCode);
    }

    private static String shipperCode(CarrierCode carrierCode) {
        return CARRIER_CODE_SHIPPER_CODE_MAPPING.get(carrierCode);
    }

    private static TrackingStatus trackingStatus(String state) {
        var status = STATE_TRACKING_STATUS_MAPPING.get(state);
        return Objects.isNull(status)
                ? TrackingStatus.UNKNOWN
                : status;
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
                var event = new DefaultTrackingEvent();
                try {
                    event.setOccurredTime(DateUtils.parseDate(this.acceptTime, "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                event.setMessage(this.acceptStation);
                return event;
            }
        }


        public Track toTracker() {
            var tracker = new InternalTrack();
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

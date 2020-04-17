package com.mallfoundry.tracking.repository.jpa.convert;

import com.mallfoundry.tracking.InternalTrackingEvent;
import com.mallfoundry.shipping.TrackingEvent;
import com.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Objects;

public class TrackingEventListConverter implements AttributeConverter<List<TrackingEvent>, String> {
    @Override
    public String convertToDatabaseColumn(List<TrackingEvent> events) {
        return Objects.isNull(events) ? null : JsonUtils.stringify(events);
    }

    @Override
    public List<TrackingEvent> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : JsonUtils.parse(dbData, List.class, InternalTrackingEvent.class);
    }
}

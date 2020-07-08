package org.mallfoundry.analytics.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
public class DateDimension {
    private static final DateTimeFormatter ID_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private String id;
    private int year;
    private int month;
    private int dayOfMonth;
    private int quarter;

    public static String idOf(Date date) {
        ZoneId zone = ZoneId.systemDefault();
        return ID_DATE_FORMATTER.format(LocalDate.ofInstant(date.toInstant(), zone));
    }
}

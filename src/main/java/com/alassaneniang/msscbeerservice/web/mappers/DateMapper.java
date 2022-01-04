package com.alassaneniang.msscbeerservice.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    public OffsetDateTime asOOffsetDateTime ( Timestamp ts ) {
        return ts != null ? OffsetDateTime.of(
                ts.toLocalDateTime().getYear(),
                ts.toLocalDateTime().getMonthValue(),
                ts.toLocalDateTime().getDayOfMonth(),
                ts.toLocalDateTime().getHour(),
                ts.toLocalDateTime().getMinute(),
                ts.toLocalDateTime().getSecond(),
                ts.toLocalDateTime().getNano(),
                ZoneOffset.UTC
        ) : null;
    }

    public Timestamp asTimeStamp ( OffsetDateTime odt ) {
        return odt != null ? Timestamp.valueOf(
                odt.atZoneSameInstant( ZoneOffset.UTC ).toLocalDateTime()
        ) : null;
    }
}

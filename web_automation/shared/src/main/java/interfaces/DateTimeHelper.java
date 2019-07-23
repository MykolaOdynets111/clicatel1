package interfaces;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface DateTimeHelper {

    default Long convertLocalDateTimeToMillis(LocalDateTime ldt, ZoneId zoneId) {
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return zdt.toInstant().toEpochMilli();
    }

    default String formTimeStringFromMillis(Long millis, ZoneId zoneId, DateTimeFormatter formatter){
        LocalDateTime itemDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId);
        return itemDateTime.format(formatter);
    }

    default LocalDateTime transformBackendTimeToZonedTime(String time, ZoneId zoneId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return LocalDateTime.parse(time, formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime();
    }
}

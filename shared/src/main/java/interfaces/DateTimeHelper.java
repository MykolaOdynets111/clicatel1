package interfaces;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    public static Long convertLocalDateTimeToMillis(LocalDateTime ldt, ZoneId zoneId) {
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return zdt.toInstant().toEpochMilli();
    }

    public static String formTimeStringFromMillis(Long millis, ZoneId zoneId, DateTimeFormatter formatter){
        LocalDateTime itemDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId);
        return itemDateTime.format(formatter);
    }

    public static LocalDateTime transformBackendTimeToZonedTime(String time, ZoneId zoneId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return LocalDateTime.parse(time, formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime();
    }

    public static String getDateTimeWithHoursShift(int hours){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime.now(ZoneOffset.UTC).minusHours(hours).format(formatter);
    }
}

package datetimeutils;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    public static final String DD_MM_YYYY = "dd/MM/yyyy";

    @NotNull
    public static DateTimeFormatter getDD_MM_YYYY_DateFormatter() {
        return DateTimeFormatter.ofPattern(DD_MM_YYYY);
    }

    public static Long convertLocalDateTimeToMillis(LocalDateTime ldt, ZoneId zoneId) {
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return zdt.toInstant().toEpochMilli();
    }

    public static String getDateTimeWithHoursShift(int hours){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime.now(ZoneOffset.UTC).minusHours(hours).format(formatter);
    }

    public static final DateTimeFormatter getDD_MM_YYYY_With_Time_Formatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' h:mm a", Locale.US);
    }

    public static final LocalDateTime parseDate(String stringDate) {
        DateTimeFormatter formatter = getDD_MM_YYYY_With_Time_Formatter();

        if (stringDate.contains("am")) {
            stringDate = stringDate.replace("am", "AM");
        } else {
            stringDate = stringDate.replace("pm", "PM");
        }

        if (stringDate.contains("Yesterday")) {
            stringDate = stringDate.replace("Yesterday", LocalDate.now().minus(Period.ofDays(1)).toString());
        } else if (stringDate.contains("Today")) {
            stringDate = stringDate.replace("Today", LocalDate.now().toString());
        }
        return LocalDateTime.parse(stringDate, formatter);
    }
}
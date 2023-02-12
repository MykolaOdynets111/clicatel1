package datetimeutils;

import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

public class DateTimeHelper {

    public static final String DD_MM_YYYY = "dd/MM/yyyy";

    @NotNull
    public static DateTimeFormatter getDD_MM_YYYY_DateFormatter() {
        return DateTimeFormatter.ofPattern(DD_MM_YYYY);
    }

    @NotNull
    public static DateTimeFormatter getYYYY_MM_DD_HH_MM() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @NotNull
    public static DateTimeFormatter getYYYY_MM_DD_HH_MM_SS() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    public static DateTimeFormatter getYYYY_MM_DD_With_Time_Formatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' h:mm a", Locale.US);
    }

    public static DateTimeFormatter getDD_MMM_YYYY_With_Time_Formatter() {
        return DateTimeFormatter.ofPattern("dd MMM. yyyy 'at' h:mm a", Locale.US);
    }

    public static Long convertLocalDateTimeToMillis(LocalDateTime ldt, ZoneId zoneId) {
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return zdt.toInstant().toEpochMilli();
    }

    public static String getDateTimeWithHoursShift(int hours) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime.now(ZoneOffset.UTC).minusHours(hours).format(formatter);
    }

    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime.now(ZoneOffset.UTC).format(formatter);
    }

    public static LocalDate parseToLocalDate(String timestamp) {
        String localDateTime = Arrays.stream(timestamp.split("\\+")).findFirst()
                .orElseThrow(NoSuchElementException::new);

        return LocalDateTime.parse(localDateTime, getYYYY_MM_DD_HH_MM_SS()).toLocalDate();
    }

    public static LocalDateTime parseDate(String stringDate) {
        DateTimeFormatter formatter;

        if (stringDate.contains("am")) {
            stringDate = stringDate.replace("am", "AM");
        } else {
            stringDate = stringDate.replace("pm", "PM");
        }

        if (stringDate.contains("Yesterday")) {
            formatter = getYYYY_MM_DD_With_Time_Formatter();
            stringDate = stringDate.replace("Yesterday", LocalDate.now().minus(Period.ofDays(1)).toString());
        } else if (stringDate.contains("Today")) {
            formatter = getYYYY_MM_DD_With_Time_Formatter();

            stringDate = stringDate.replace("Today", LocalDate.now().toString());
        } else {
            formatter = getDD_MMM_YYYY_With_Time_Formatter();
        }
        return LocalDateTime.parse(stringDate, formatter);
    }
}
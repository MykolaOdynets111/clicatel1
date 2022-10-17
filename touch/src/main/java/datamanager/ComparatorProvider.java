package datamanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class ComparatorProvider {

    public static final Comparator<String> DATE_COMPARATOR = new Comparator<String>() {
        final DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public int compare(String a, String b) {
            if (a.equals("")) {
                return (b.equals("")) ? 0 : 1;
            } else if (b.equals("")) {
                return 1;
            } else
                try {
                    return f.parse(a).compareTo(f.parse(b));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
        }
    };
}

package ur.mi.android.wgplus05;

/**
 * Created by simon on 07.09.2017.
 */
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarItem  implements Comparable<CalendarItem> {
    private String name;
    private GregorianCalendar cal;

    public CalendarItem(String name, int day, int month, int year) {
        this.name = name;
        cal = new GregorianCalendar(year, month, day);
    }


    public String getName() {
        return name;
    }

    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(cal.getTime());
    }

    public Date getDueDate() {
        return cal.getTime();
    }

    @Override
    public int compareTo(CalendarItem another) {
        return getDueDate().compareTo(another.getDueDate());
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate();
    }
}

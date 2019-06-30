package cs.orders.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    static final double DELTA = 0.001;

    static final double QUANTITY = 2544.12;
    static final double PRICE = 42.42;

    static final String INSTRUMENT_ID = "CHF";
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    static Date ENTRY_DATE;

    static {
        try {
            ENTRY_DATE = DATE_FORMAT.parse("2019-06-20");
        } catch (ParseException e) {
            // shouldn't happen
            e.printStackTrace();
            System.exit(1);
        }
    }

}

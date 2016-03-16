import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = format.parse("01/02/2016");
        Date endDate = format.parse("09/03/2016");

        RangeRates obj = new RangeRates(startDate, endDate, "R01235");
        ArrayList<DateCurrency> rates = obj.getRates();
    }
}

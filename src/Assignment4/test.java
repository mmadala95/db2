package Assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String args[]) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date x = simpleDateFormat.parse("2020-02-01");
        Date y = simpleDateFormat.parse("2020-03-31");
        Date z = simpleDateFormat.parse("2020-03-01");
        if (!z.before(x) && !z.after(y)) {
            System.out.println(new Date(y.getTime()));
        }


    }
}

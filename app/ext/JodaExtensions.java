package ext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.libs.I18N;
import play.templates.JavaExtensions;

/**
 * Created with IntelliJ IDEA.
 * User: pascalabaziou
 * Date: 20/06/2014
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
public class JodaExtensions extends JavaExtensions {

    public static String format(DateTime dateTime) {
        return format(dateTime, I18N.getDateFormat());
    }

    public static String format(DateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(dateTime);
    }

}

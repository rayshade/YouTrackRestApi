package youtrack.util;

import java.net.URLEncoder;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
class Service {

    public final static String ENC = "UTF-8";

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, ENC);
        } catch (Exception ex) {
            return str;
        }
    }
}

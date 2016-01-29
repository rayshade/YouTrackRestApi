package youtrack.util;
import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class Service {
    public final static String ENC = "UTF-8";
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, ENC);
        } catch(Exception ex) {
            return str;
        }
    }
    public static String readStream(final @Nonnull InputStream stream) throws IOException {
        final InputStreamReader is = new InputStreamReader(stream, ENC);
        final StringBuilder sb = new StringBuilder();
        final BufferedReader br = new BufferedReader(is);
        String read = br.readLine();
        while(read != null) {
            sb.append(read);
            read = br.readLine();
        }
        return sb.toString();
    }
}
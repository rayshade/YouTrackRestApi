package youtrack;

import org.jetbrains.annotations.NotNull;
import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import youtrack.exceptions.CommandExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
final class Login extends RunningCommand<YouTrack, String> {
    Login(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    boolean usesAuthorization() {
        return false;
    }

    @Override
    CommandResult<String> getResult() {
        final CommandResult<String> result = new CommandResult<String>(this);
        try {
            final Object resultObject = objectFromXml(EntityUtils.toString(response.getEntity()));
            if (resultObject instanceof LoginResult) {
                if (((LoginResult) resultObject).getMessage().equalsIgnoreCase("ok")) {
                    result.setResult(response.getFirstHeader("Set-Cookie").getValue());
                }
            } else if (resultObject instanceof Error) {
                result.setStatus((Error) resultObject);
            }
            result.setStatus(response.getStatusLine());
        } catch (Exception e) {
            result.setException(new CommandExecutionException(this, e));
        }
        return result;
    }

    @Override
    HttpRequestBase createMethod() {
        final HttpPost result = new HttpPost(owner.getYouTrack().getHostAddress() + "user/login");
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", parameters.get("login")));
        params.add(new BasicNameValuePair("password", parameters.get("password")));
        result.setEntity(new UrlEncodedFormEntity(params, Charsets.toCharset("UTF-8")));
        return result;
    }
}
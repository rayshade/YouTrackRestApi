package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
class ModifyIssueField extends RunningCommand<Issue, String> {
    ModifyIssueField(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    CommandResult<String> getResult() {
        final CommandResult<String> result = new CommandResult<String>(this);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                final Error e = new Error();
                e.setMessage(response.getStatusLine().getReasonPhrase());
                e.setCode(statusCode);
                result.setStatus(e);
            } else {
                result.setResult(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            result.setException(new CommandExecutionException(this, e));
        } finally {
            if (response != null) result.setStatus(response.getStatusLine());
        }
        return result;
    }

    @Override
    HttpRequestBase createMethod() {
        final HttpPost result = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/createMethod");
        result.setEntity(new UrlEncodedFormEntity(Collections.singletonList(new BasicNameValuePair(
                "command", parameters.get("field") + " " + parameters.get("value"))),Charsets.toCharset("UTF-8")));
        return result;
    }
}

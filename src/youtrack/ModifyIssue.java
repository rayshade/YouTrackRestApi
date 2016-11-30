package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import youtrack.exceptions.CommandExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
final class ModifyIssue extends RunningCommand<Issue, String> {
    ModifyIssue(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() throws IOException, CommandExecutionException {
        final HttpPost result = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId());
        final String summary = parameters.get("summary");
        final String description = parameters.get("description");
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("summary", ((summary == null) ? owner.getSummary() : summary)));
        params.add(new BasicNameValuePair("description", ((description == null) ? owner.getDescription() : description)));
        result.setEntity(new UrlEncodedFormEntity(params,Charsets.toCharset("UTF-8")));
        return result;
    }
}
package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
class RemoveIssueLink extends RemoveCommand<Issue, IssueLink> {
    RemoveIssueLink(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        final HttpPost result = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/createMethod");
        List<NameValuePair> commandParams = new ArrayList<NameValuePair>();
        commandParams.add(new BasicNameValuePair("command", "remove " + item.getTypeOutward() + " " + item.getTarget()));
        result.setEntity(new UrlEncodedFormEntity(commandParams,Charsets.toCharset("UTF-8")));
        return result;
    }
}

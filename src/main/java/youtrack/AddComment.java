package youtrack;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
final class AddComment extends AddCommand<Issue, IssueComment> {
    AddComment(@NotNull Issue owner) {
        super(owner);
    }

    HttpPost result;

    @Override
    HttpRequestBase createMethod() {

        final List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("command", "comment"));
        params.add(new BasicNameValuePair("comment", item.getText()));
        final String visibility = item.getVisibility();
        if (visibility != null && !visibility.isEmpty())
            params.add(new BasicNameValuePair("group", visibility));

        return result = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" +
                getOwner().getId() + "/execute?" + URLEncodedUtils.format(params, "UTF-8"));
    }
}
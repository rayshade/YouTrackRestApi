package youtrack;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
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

        return result = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" +
                getOwner().getId() + "/execute?" + URLEncodedUtils.format(params, "UTF-8"));
    }
}
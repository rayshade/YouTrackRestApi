package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import java.util.Collections;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class ChangeIssueVotes extends RunningCommand<Issue, Boolean> {
    public ChangeIssueVotes(@NotNull Issue owner) {
        super(owner);
    }

    public ChangeIssueVotes(@NotNull Issue owner, final boolean voteUp) {
        this(owner);
        setParameter("vote", (voteUp ? "vote" : "unvote"));
    }

    @Override
    HttpRequestBase createMethod() {
        final HttpPost result = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" + getOwner().getId() + "/createMethod");
        result.setEntity(new UrlEncodedFormEntity(Collections.singletonList(new BasicNameValuePair("command", parameters.get("vote"))),Charsets.toCharset("UTF-8")));
        return result;
    }
}

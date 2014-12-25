package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class ChangeIssueVotes extends RunningCommand<Issue, Boolean> {

    public ChangeIssueVotes(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public Boolean getResult() {
        return null;
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        PostMethod postMethod = new PostMethod(baseHost + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", ((getArgument()) ? "vote" : "unvote"))});
        return postMethod;
    }
}

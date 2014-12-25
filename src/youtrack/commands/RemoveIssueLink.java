package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueLink;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveIssueLink extends RemoveCommand<Issue,IssueLink> {

    public RemoveIssueLink(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        final PostMethod postMethod = new PostMethod(baseHost + "issue/" + owner.getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", "remove " + item.getTypeOutward() + " " + item.getTarget())
        });
        return postMethod;
    }
}

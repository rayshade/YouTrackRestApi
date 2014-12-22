package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueLink;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveIssueLink extends Command {
    private final Issue issue;
    private final IssueLink link;

    public RemoveIssueLink(Issue issue, IssueLink link) {
        this.issue = issue;
        this.link = link;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
    }

    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        PostMethod postMethod = new PostMethod(baseHost + "issue/" + issue.getId() + "/execute");

        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", "remove " + link.getTypeOutward() + " " + link.getTarget())

        });

        return postMethod;
    }
}

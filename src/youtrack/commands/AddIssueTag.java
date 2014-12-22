package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueTag;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class AddIssueTag extends Command {
    private final Issue issue;
    private final IssueTag issueTag;

    public AddIssueTag(Issue issue, IssueTag issueTag) {

        this.issue = issue;
        this.issueTag = issueTag;
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

        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", "tag " + issueTag.getTag())});

        return postMethod;
    }
}

package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueComment;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class AddComment extends Command {
    private final String comment;
    private final Issue issue;

    public AddComment(Issue issue, IssueComment comment) {
        this.comment = comment.getText();
        this.issue = issue;
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
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("comment", comment)});
        return postMethod;
    }
}
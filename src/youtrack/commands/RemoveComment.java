package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.IssueComment;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class RemoveComment extends Command {
    private final Issue issue;
    private final String commentId;

    public RemoveComment(Issue issue, IssueComment comment) {
        this.issue = issue;
        commentId = comment.getId();
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
        DeleteMethod deleteMethod = new DeleteMethod(baseHost + "issue/" + issue.getId() + "/comment/" + commentId);
        HttpMethodParams params = new HttpMethodParams();
        params.setBooleanParameter("permanently", true);
        deleteMethod.setParams(params);
        return deleteMethod;
    }

}

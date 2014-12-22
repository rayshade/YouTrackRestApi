package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.CommentList;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueComments extends Command<List<IssueComment>> {
    private final Issue issue;

    public GetIssueComments(Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
    }

    @Override

    public List<IssueComment> getResult() throws CommandExecutionException {
        try {

            CommentList commentList = (CommentList) objectFromXml(method.getResponseBodyAsString());

            return commentList.getComments();

        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + issue.getId() + "/comment");
        return method;
    }
}
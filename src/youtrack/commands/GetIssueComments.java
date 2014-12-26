package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.CommentList;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.commands.base.ListCommand;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueComments extends ListCommand<Issue, IssueComment> {

    public GetIssueComments(@NotNull Issue owner) {
        super(owner);
    }

    @NotNull
    @Override
    public List<IssueComment> getResult() throws CommandExecutionException {
        try {
            final CommentList commentList = (CommentList) objectFromXml(method.getResponseBodyAsString());
            final List<IssueComment> list = commentList.getComments();
            return list != null ? list : Collections.<IssueComment>emptyList();
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + owner.getId() + "/comment");
        return method;
    }
}
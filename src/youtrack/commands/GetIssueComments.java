package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.CommentList;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.commands.base.ListCommand;
import youtrack.util.Service;

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
    public List<IssueComment> getResult() throws Exception {
        final String responseBodyAsString = Service.readStream(method.getResponseBodyAsStream());
        final CommentList commentList = (CommentList) objectFromXml(responseBodyAsString);
        final List<IssueComment> list = commentList.getItems();
        if(list == null) return Collections.emptyList();
        for(IssueComment comment : list) {
            comment.setOwner(owner);
        }
        return list;
    }
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/comment");
    }
}
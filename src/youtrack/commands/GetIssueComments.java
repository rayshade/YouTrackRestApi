package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.commands.base.ListCommand;
/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueComments extends ListCommand<Issue, IssueComment> {
    public GetIssueComments(@NotNull Issue owner) {
        super(owner);
    }
 /*   @NotNull
    @Override
    public List<IssueComment> getResult() throws AuthenticationErrorException, CommandExecutionException {
        List<IssueComment> list;
        try {
            final String responseBodyAsString = Service.readStream(method.getResponseBodyAsStream());
            final CommentList commentList = (CommentList) objectFromXml(responseBodyAsString);
            list = commentList.getItems();
            if(list == null) return Collections.emptyList();
            for(IssueComment comment : list) {
                comment.setOwner(owner);
            }
        } catch(Exception e) {
            throw new CommandExecutionException(this, e);
        }
        return list;
    }*/
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/comment");
    }
}
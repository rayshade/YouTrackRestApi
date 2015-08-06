package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.YouTrack;
import youtrack.commands.base.ListCommand;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetIssues extends ListCommand<YouTrack, Issue> {
    public GetIssues(@NotNull YouTrack owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/byproject/" + getOwner().getId());
    }
}
package youtrack.commands;
import org.apache.commons.httpclient.methods.DeleteMethod;
import youtrack.Issue;
import youtrack.YouTrack;
import youtrack.commands.base.RemoveCommand;
import youtrack.exceptions.NoSuchIssueFieldException;

import javax.annotation.Nonnull;
import java.io.IOException;
/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class RemoveIssue extends RemoveCommand<YouTrack, Issue> {
    public RemoveIssue(@Nonnull YouTrack owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() throws IOException, NoSuchIssueFieldException {
        method = new DeleteMethod(owner.getYouTrack().getHostAddress() + "/issue/" + item.getId());
    }
}

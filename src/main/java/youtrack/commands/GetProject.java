package youtrack.commands;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Project;
import youtrack.YouTrack;
import youtrack.commands.base.SingleItemCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import javax.annotation.Nonnull;
import java.io.IOException;
/**
 * Created by Egor.Malyshev on 26.12.2014.
 */
public class GetProject extends SingleItemCommand<YouTrack, Project> {
    public GetProject(@Nonnull YouTrack owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "project/all");
    }
}

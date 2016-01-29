package youtrack.commands;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.YouTrack;
import youtrack.commands.base.AddCommand;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class AddIssue extends AddCommand<YouTrack, Issue> {
    public AddIssue(@Nonnull YouTrack owner) {
        super(owner);
    }
    @Override
    public Issue getResult() throws CommandExecutionException, AuthenticationErrorException {
        final String[] locations = method.getResponseHeader("Location").getValue().split("/");
        final String issueId = locations[locations.length - 1];
        return owner.issues.item(issueId);
    }
    @Override
    public void createCommandMethod() throws Exception {
        method = new PutMethod(owner.getYouTrack().getHostAddress() + "issue");
        HttpMethodParams params = new HttpMethodParams();
        params.setParameter("project", item.getProjectId());
        params.setParameter("summary", getItem().getSummary());
        params.setParameter("description", getItem().getDescription());
        method.setParams(params);
    }
}
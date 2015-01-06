package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.Project;
import youtrack.commands.base.AddCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class AddIssue extends AddCommand<Project, Issue> {

    public AddIssue(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public Issue getResult() throws CommandExecutionException {
        try {
            final String[] locations = method.getResponseHeader("Location").getValue().split("/");
            final String issueId = locations[locations.length - 1];
            return owner.issues.item(issueId);
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public void createCommandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new PutMethod(baseHost + "issue");
        HttpMethodParams params = new HttpMethodParams();
        params.setParameter("project", owner.getId());
        try {
            params.setParameter("summary", getItem().getSummary());
            params.setParameter("description", getItem().getDescription());
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
        method.setParams(params);
    }
}
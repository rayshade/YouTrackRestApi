package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.Project;
import youtrack.commands.base.AddCommand;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class AddIssue extends AddCommand<Project, Issue> {
    public AddIssue(@NotNull Project owner) {
        super(owner);
    }
    @Override
    public Issue getResult() throws Exception {
        final String[] locations = method.getResponseHeader("Location").getValue().split("/");
        final String issueId = locations[locations.length - 1];
        return owner.issues.item(issueId);
    }
    @Override
    public void createCommandMethod() throws Exception {
        method = new PutMethod(owner.getYouTrack().getHostAddress() + "issue");
        HttpMethodParams params = new HttpMethodParams();
        params.setParameter("project", owner.getId());
        params.setParameter("summary", getItem().getSummary());
        params.setParameter("description", getItem().getDescription());
        method.setParams(params);
    }
}
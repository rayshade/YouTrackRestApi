package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.Project;
import youtrack.commands.base.SingleItemCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.util.Service;

import java.io.IOException;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends SingleItemCommand<Project, Issue> {

    public GetIssue(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public Issue getResult() throws Exception {
        String responseBodyAsString;
        try {
            responseBodyAsString = Service.readStream(method.getResponseBodyAsStream());
        } catch (IOException e) {
            throw new CommandExecutionException(this, e);
        }
        final Issue issue = (Issue) objectFromXml(responseBodyAsString);
        issue.setOwner(owner);
        return issue;
    }

    @Override
    public void createCommandMethod() {
        final StringBuilder sb = new StringBuilder();
        sb.append(owner.getYouTrack().getHostAddress()).append("issue/").append(itemId);
        if (!parameters.isEmpty()) {
            sb.append("?");
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(parameters.get(name)).append("&");
            }
        }
        method = new GetMethod(sb.toString());
    }
}
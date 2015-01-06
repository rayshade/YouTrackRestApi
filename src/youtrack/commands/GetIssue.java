package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.Project;
import youtrack.commands.base.SingleItemCommand;
import youtrack.exceptions.CommandExecutionException;

import java.io.IOException;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends SingleItemCommand<Project, Issue> {

    public GetIssue(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public Issue getResult() throws CommandExecutionException {
        String responseBodyAsString;
        try {
            responseBodyAsString = method.getResponseBodyAsString();
        } catch (IOException e) {
            throw new CommandExecutionException(this, e);
        }
        return (Issue) objectFromXml(responseBodyAsString);
    }

    @Override
    public void createCommandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + itemId);
    }
}
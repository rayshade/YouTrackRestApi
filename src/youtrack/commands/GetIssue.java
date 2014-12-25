package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.Project;
import youtrack.exceptions.CommandExecutionException;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends SingleItemCommand<Project, Issue> {

    public GetIssue(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public Issue getResult() throws CommandExecutionException {
        try {
            return (Issue) objectFromXml(method.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new CommandExecutionException(this, ex);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + getItemId());
        return method;
    }
}
package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.exceptions.CommandExecutionException;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends Command<Issue> {
    private final String id;

    public GetIssue(String id) {
        this.id = id;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
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

        method = new GetMethod(baseHost + "issue/" + id);

        return method;

    }

}
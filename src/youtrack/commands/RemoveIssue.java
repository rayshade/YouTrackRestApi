package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import youtrack.Issue;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class RemoveIssue extends Command {
    private final Issue issue;

    public RemoveIssue(Issue issue) {

        this.issue = issue;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
    }

    @Override

    public Object getResult() throws CommandExecutionException {
        return null;
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException {
        return new DeleteMethod(baseHost + "/issue/" + issue.getId());
    }
}

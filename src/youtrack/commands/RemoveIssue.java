package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import youtrack.Issue;
import youtrack.Project;
import youtrack.commands.base.RemoveCommand;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class RemoveIssue extends RemoveCommand<Project, Issue> {

    public RemoveIssue(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException {
        return new DeleteMethod(baseHost + "/issue/" + item.getId());
    }
}

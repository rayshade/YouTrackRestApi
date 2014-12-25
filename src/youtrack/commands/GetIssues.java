package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueProjectList;
import youtrack.Project;
import youtrack.commands.base.ListCommand;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetIssues extends ListCommand<Project, Issue> {

    public GetIssues(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public List<Issue> getResult() throws CommandExecutionException {
        try {
            IssueProjectList itemList = (IssueProjectList) objectFromXml(method.getResponseBodyAsString());
            return itemList.getItems();
        } catch (Exception ex) {
            throw new CommandExecutionException(this, ex);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/byproject/" + getOwner().getId());
        return method;
    }
}

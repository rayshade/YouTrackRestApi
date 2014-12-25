package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.IssueProjectList;
import youtrack.Project;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.List;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class QueryIssues extends QueryCommand<Project, Issue> {

    public QueryIssues(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public boolean usesAuthorization() {
        return true;
    }

    @Override
    public List<Issue> getResult() throws CommandExecutionException {
        try {
            final IssueProjectList itemList = (IssueProjectList) objectFromXml(method.getResponseBodyAsString());
            return itemList.getItems();
        } catch (Exception ex) {
            throw new CommandExecutionException(this, ex);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(baseHost + "issue/byproject/" + owner.getId());
        final HttpMethodParams params = new HttpMethodParams();
        if (parameters.getFilter() != null) params.setParameter("filter", parameters.getFilter());
        if (parameters.getMax() != 0) params.setIntParameter("max", parameters.getMax());
        if (parameters.getStart() != 0) params.setIntParameter("after", parameters.getStart());
        method.setParams(params);
        return method;
    }
}

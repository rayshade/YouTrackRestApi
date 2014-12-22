package youtrack.commands;

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
public class QueryIssues extends Command<List<Issue>> {
    private final Project project;
    private final QueryParameters queryParameters;

    public QueryIssues(Project project, QueryParameters params) {
        this.project = project;
        this.queryParameters = params;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
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
    public HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(baseHost + "issue/byproject/" + project.getId());

        HttpMethodParams params = new HttpMethodParams();

        if (queryParameters.getFilter() != null) params.setParameter("filter", queryParameters.getFilter());
        if (queryParameters.getMax() != 0) params.setIntParameter("max", queryParameters.getMax());
        if (queryParameters.getStart() != 0) params.setIntParameter("after", queryParameters.getStart());

        method.setParams(params);

        return method;
    }
}

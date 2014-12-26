package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueProjectList;
import youtrack.Project;
import youtrack.commands.base.QueryCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class QueryIssues extends QueryCommand<Project, Issue> {

    public QueryIssues(@NotNull Project owner) {
        super(owner);
    }

    @Override
    public List<Issue> getResult() throws CommandExecutionException {
        try {
            final IssueProjectList itemList = (IssueProjectList) objectFromXml(method.getResponseBodyAsString());
            final List<Issue> items = itemList.getItems();
            return items != null ? items : Collections.<Issue>emptyList();
        } catch (Exception ex) {
            throw new CommandExecutionException(this, ex);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(baseHost + "issue/byproject/" + owner.getId());
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (parameters.getQuery() != null) {
            params.add(new NameValuePair("filter", parameters.getQuery()));
        }
        if (parameters.getMax() != 0) {
            params.add(new NameValuePair("max", String.valueOf(parameters.getMax())));
        }
        if (parameters.getStart() != 0) {
            params.add(new NameValuePair("after", String.valueOf(parameters.getStart())));
        }
        method.setQueryString(params.toArray(new NameValuePair[params.size()]));
        return method;
    }
}
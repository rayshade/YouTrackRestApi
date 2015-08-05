package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.Project;
import youtrack.commands.base.QueryCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class QueryIssues extends QueryCommand<Project, Issue> {
    public QueryIssues(@NotNull Project owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/byproject/" + owner.getId());
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(parameters.get("query") != null) {
            params.add(new NameValuePair("filter", parameters.get("query")));
        }
        if(parameters.get("max") != null) {
            params.add(new NameValuePair("max", parameters.get("max")));
        }
        if(parameters.get("start") != null) {
            params.add(new NameValuePair("after", parameters.get("start")));
        }
        method.setQueryString(params.toArray(new NameValuePair[params.size()]));
    }
}
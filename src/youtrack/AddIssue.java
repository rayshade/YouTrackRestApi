package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import youtrack.exceptions.CommandExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
class AddIssue extends AddCommand<YouTrack, Issue> {
    AddIssue(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    CommandResult<Issue> getResult() {
        final String[] locations = response.getFirstHeader("Location").getValue().split("/");
        final String issueId = locations[locations.length - 1];
        final CommandResult<Issue> result = new CommandResult<Issue>(this);
        try {
            result.setResult(owner.issues.item(issueId));
        } catch (CommandExecutionException ce) {
            result.setException(ce);
        } catch (Exception e) {
            result.setException(new CommandExecutionException(this, e));
        } finally {
            if (response != null) result.setStatus(response.getStatusLine());
        }
        return result;
    }

    @Override
    HttpRequestBase createMethod() throws IOException, CommandExecutionException {
        final HttpPut result = new HttpPut(owner.getYouTrack().getHostAddress() + "issue");
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("project", item.getProjectId()));
        params.add(new BasicNameValuePair("summary", getItem().getSummary()));
        params.add(new BasicNameValuePair("description", getItem().getDescription()));
        result.setEntity(new UrlEncodedFormEntity(params,Charsets.toCharset("UTF-8")));
        return result;
    }
}
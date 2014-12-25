package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.exceptions.CommandExecutionException;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class ModifyIssueField extends RunningCommand<Issue, String> {

    public ModifyIssueField(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public String getResult() throws CommandExecutionException {
        try {
            return method.getStatusCode() == 200 ? null : method.getResponseBodyAsString();
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        PostMethod postMethod = new PostMethod(baseHost + "issue/" + owner.getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", getArguments().get("field") + " " + getArguments().get("value"))
        });
        method = postMethod;
        return method;
    }
}

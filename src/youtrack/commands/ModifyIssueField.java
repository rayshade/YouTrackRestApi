package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.commands.base.RunningCommand;
import youtrack.util.Service;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class ModifyIssueField extends RunningCommand<Issue, String> {

    public ModifyIssueField(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public String getResult() throws Exception {
        return method.getStatusCode() == 200 ? null : Service.readStream(method.getResponseBodyAsStream());
    }

    @Override
    public void createCommandMethod() {
        PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", getArguments().get("field") + " " + getArguments().get("value"))
        });
        method = postMethod;
    }
}

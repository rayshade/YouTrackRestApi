package youtrack.commands;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Error;
import youtrack.Issue;
import youtrack.commands.base.RunningCommand;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.util.Service;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class ModifyIssueField extends RunningCommand<Issue, String> {
    public ModifyIssueField(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public String getResult() throws CommandExecutionException, AuthenticationErrorException {
        try {
            if(method.getStatusCode() != 200) {
                final Error e = new Error();
                e.setMessage(method.getStatusText());
                e.setCode(method.getStatusCode());
                throw new CommandExecutionException(this, e);
            } else {
                return Service.readStream(method.getResponseBodyAsStream());
            }
        } catch(CommandExecutionException e) {
            throw e;
        } catch(Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }
    @Override
    public void createCommandMethod() {
        final PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", parameters.get("field") + " " + parameters.get("value"))
        });
        method = postMethod;
    }
}

package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueLink;
import youtrack.commands.base.RemoveCommand;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveIssueLink extends RemoveCommand<Issue, IssueLink> {

    public RemoveIssueLink(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public void createCommandMethod() {
        final PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", "remove " + item.getTypeOutward() + " " + item.getTarget())
        });
        method = postMethod;
    }
}

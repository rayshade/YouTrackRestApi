package youtrack.commands;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueLink;
import youtrack.commands.base.AddCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class AddIssueLink extends AddCommand<Issue, IssueLink> {
    public AddIssueLink(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public IssueLink getResult() {
        return null;
    }
    @Override
    public void createCommandMethod() {
        final PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", getItem().getTypeName() + getItem().getTarget())});
        method = postMethod;
    }
}

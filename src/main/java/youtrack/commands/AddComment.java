package youtrack.commands;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.commands.base.AddCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class AddComment extends AddCommand<Issue, IssueComment> {
    public AddComment(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public IssueComment getResult() {
        return null;
    }
    @Override
    public void createCommandMethod() {
        final PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("comment", item.getText())});
        method = postMethod;
    }
}
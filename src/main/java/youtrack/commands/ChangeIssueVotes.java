package youtrack.commands;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.commands.base.RunningCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class ChangeIssueVotes extends RunningCommand<Issue, Boolean> {
    public ChangeIssueVotes(@Nonnull Issue owner) {
        super(owner);
    }
    public ChangeIssueVotes(@Nonnull Issue owner, final boolean voteUp) {
        this(owner);
        addParameter("vote", (voteUp ? "vote" : "unvote"));
    }
    @Override
    public void createCommandMethod() throws Exception {
        final PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", parameters.get("vote"))});
        method = postMethod;
    }
}

package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.commands.base.RunningCommand;
import youtrack.exceptions.CommandExecutionException;

import java.util.HashMap;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class ChangeIssueVotes extends RunningCommand<Issue, Boolean> {

    public ChangeIssueVotes(@NotNull Issue owner) {
        super(owner);
    }

    public ChangeIssueVotes(@NotNull Issue owner, final boolean voteUp) {
        this(owner);
        arguments = new HashMap<String, Boolean>();
        arguments.put("vote", voteUp);
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) throws CommandExecutionException {
        final PostMethod postMethod = new PostMethod(baseHost + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", (getArguments().get("vote") ? "vote" : "unvote"))});
        return postMethod;
    }
}

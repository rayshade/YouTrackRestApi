package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueTag;
import youtrack.commands.base.AddCommand;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class AddIssueTag extends AddCommand<Issue, IssueTag> {

    public AddIssueTag(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public IssueTag getResult() {
        return null;
    }

    @Override
    public void createCommandMethod(String baseHost) {
        final PostMethod postMethod = new PostMethod(baseHost + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", "tag " + getItem().getTag())});
        method = postMethod;
    }
}

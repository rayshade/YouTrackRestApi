package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.commands.base.AddCommand;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class AddComment extends AddCommand<Issue, IssueComment> {
    public AddComment(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public IssueComment getResult() {
        return null;
    }

    @Override
    public void createCommandMethod(String baseHost) {
        final PostMethod postMethod = new PostMethod(baseHost + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("comment", item.getText())});
        method = postMethod;
    }
}
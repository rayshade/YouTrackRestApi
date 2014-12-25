package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueComment;

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
    public HttpMethodBase commandMethod(String baseHost) {
        PostMethod postMethod = new PostMethod(baseHost + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("comment", item.getText())});
        return postMethod;
    }
}
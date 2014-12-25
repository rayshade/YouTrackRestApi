package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueLink;
import youtrack.commands.base.AddCommand;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class AddIssueLink extends AddCommand<Issue, IssueLink> {

    public AddIssueLink(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public IssueLink getResult() {
        return null;
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        PostMethod postMethod = new PostMethod(baseHost + "issue/" + getOwner().getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("command", getItem().getTypeName() + getItem().getTarget())});
        return postMethod;
    }
}

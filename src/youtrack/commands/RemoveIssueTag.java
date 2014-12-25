package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueTag;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class RemoveIssueTag extends RemoveCommand<Issue, IssueTag> {

    public RemoveIssueTag(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        final PostMethod postMethod = new PostMethod(baseHost + "issue/" + owner.getId() + "/execute");
        postMethod.setRequestBody(new NameValuePair[]{
                new NameValuePair("command", "untag " + item.getTag())
        });
        return postMethod;
    }
}

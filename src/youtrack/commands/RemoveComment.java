package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.IssueComment;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class RemoveComment extends RemoveCommand<Issue, IssueComment> {

    public RemoveComment(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public IssueComment getResult() {
        return null;
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        final DeleteMethod deleteMethod = new DeleteMethod(baseHost + "issue/" + owner.getId() + "/comment/" + item.getId());
        final HttpMethodParams params = new HttpMethodParams();
        params.setBooleanParameter("permanently", true);
        deleteMethod.setParams(params);
        return deleteMethod;
    }
}

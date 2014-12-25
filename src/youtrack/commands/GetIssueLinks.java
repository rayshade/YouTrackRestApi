package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueLink;
import youtrack.LinkList;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueLinks extends ListCommand<Issue, IssueLink> {

    public GetIssueLinks(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public List<IssueLink> getResult() throws CommandExecutionException {
        try {
            final LinkList linkList = (LinkList) objectFromXml(method.getResponseBodyAsString());
            return linkList.getItems();
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + owner.getId() + "/link");
        return method;
    }
}
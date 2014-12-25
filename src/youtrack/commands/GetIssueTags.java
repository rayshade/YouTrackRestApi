package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueTag;
import youtrack.TagList;
import youtrack.commands.base.ListCommand;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class GetIssueTags extends ListCommand<Issue, IssueTag> {

    public GetIssueTags(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public List<IssueTag> getResult() throws CommandExecutionException {
        try {
            TagList tagList = (TagList) objectFromXml(method.getResponseBodyAsString());
            return tagList.getItems();
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + owner.getId() + "/tags/");
        return method;
    }
}
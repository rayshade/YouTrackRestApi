package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueTag;
import youtrack.TagList;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class GetIssueTags extends Command<List<IssueTag>> {
    private final Issue issue;

    public GetIssueTags(Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
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
        method = new GetMethod(baseHost + "issue/" + issue.getId() + "/tags/");
        return method;
    }
}
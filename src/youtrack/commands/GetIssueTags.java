package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueTag;
import youtrack.TagList;
import youtrack.commands.base.ListCommand;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class GetIssueTags extends ListCommand<Issue, IssueTag> {

    public GetIssueTags(@NotNull Issue owner) {
        super(owner);
    }

    @NotNull
    @Override
    public List<IssueTag> getResult() throws Exception {
        final TagList tagList = (TagList) objectFromXml(method.getResponseBodyAsString());
        final List<IssueTag> items = tagList.getItems();
        return items != null ? items : Collections.<IssueTag>emptyList();
    }

    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/tags/");
    }
}
package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueTag;
import youtrack.TagList;
import youtrack.commands.base.ListCommand;
import youtrack.util.Service;

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
        final TagList tagList = (TagList) objectFromXml(Service.readStream(method.getResponseBodyAsStream()));
        final List<IssueTag> items = tagList.getItems();
        if (items == null) return Collections.emptyList();
        for (IssueTag tag : items) {
            tag.setOwner(owner);
        }
        return items;
    }

    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/tags/");
    }
}
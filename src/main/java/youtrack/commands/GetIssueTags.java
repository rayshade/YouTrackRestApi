package youtrack.commands;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueTag;
import youtrack.commands.base.ListCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class GetIssueTags extends ListCommand<Issue, IssueTag> {
    public GetIssueTags(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/tags/");
    }
}
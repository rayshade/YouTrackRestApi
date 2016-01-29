package youtrack.commands;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.ListCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class GetIssueAttachments extends ListCommand<Issue, IssueAttachment> {
    public GetIssueAttachments(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/attachment");
    }
}
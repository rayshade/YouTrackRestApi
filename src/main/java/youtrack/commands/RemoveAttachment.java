package youtrack.commands;
import org.apache.commons.httpclient.methods.DeleteMethod;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.RemoveCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveAttachment extends RemoveCommand<Issue, IssueAttachment> {
    public RemoveAttachment(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() {
        method = new DeleteMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/attachment/" + item.getId());
    }
}

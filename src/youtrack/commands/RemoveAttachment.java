package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.DeleteMethod;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.RemoveCommand;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveAttachment extends RemoveCommand<Issue, IssueAttachment> {

    public RemoveAttachment(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    public void createCommandMethod(String baseHost) {
        method = new DeleteMethod(baseHost + "issue/" + owner.getId() + "/attachment/" + item.getId());
    }
}

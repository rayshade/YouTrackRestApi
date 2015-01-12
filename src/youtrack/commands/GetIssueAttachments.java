package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.AttachmentList;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.ListCommand;

import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class GetIssueAttachments extends ListCommand<Issue, IssueAttachment> {

    public GetIssueAttachments(@NotNull Issue owner) {
        super(owner);
    }

    @NotNull
    @Override
    public List<IssueAttachment> getResult() throws Exception {
        final String responseBodyAsString = method.getResponseBodyAsString();
        final AttachmentList attachmentList = (AttachmentList) objectFromXml(responseBodyAsString);
        final List<IssueAttachment> list = attachmentList.getItems();
        return list != null ? list : Collections.<IssueAttachment>emptyList();
    }

    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/attachment");
    }
}

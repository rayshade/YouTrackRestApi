package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.AttachmentList;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.ListCommand;
import youtrack.util.Service;

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
        final String responseBodyAsString = Service.readStream(method.getResponseBodyAsStream());
        final AttachmentList attachmentList = (AttachmentList) objectFromXml(responseBodyAsString);
        final List<IssueAttachment> list = attachmentList.getItems();
        if(list == null) return Collections.emptyList();
        for(IssueAttachment attachment : list) {
            attachment.setOwner(owner);
        }
        return list;
    }
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/attachment");
    }
}
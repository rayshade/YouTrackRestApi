package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.AttachmentList;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.ListCommand;
import youtrack.exceptions.CommandExecutionException;

import java.io.IOException;
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
    public List<IssueAttachment> getResult() throws CommandExecutionException {
        String responseBodyAsString;
        try {
            responseBodyAsString = method.getResponseBodyAsString();
        } catch (IOException e) {
            throw new CommandExecutionException(this, e);
        }
        final AttachmentList attachmentList = (AttachmentList) objectFromXml(responseBodyAsString);
        final List<IssueAttachment> list = attachmentList.getAttachments();
        return list != null ? list : Collections.<IssueAttachment>emptyList();
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + owner.getId() + "/attachment");
        return method;
    }
}

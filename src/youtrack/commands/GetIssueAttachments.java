package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.AttachmentList;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class GetIssueAttachments extends Command<List<IssueAttachment>> {
    private final Issue issue;

    public GetIssueAttachments(Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
    }

    @Override
    public List<IssueAttachment> getResult() throws CommandExecutionException {

        try {

            AttachmentList attachmentList = (AttachmentList) objectFromXml(method.getResponseBodyAsString());

            return attachmentList.getAttachments();

        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {
        method = new GetMethod(baseHost + "issue/" + issue.getId() + "/attachment");

        return method;
    }
}

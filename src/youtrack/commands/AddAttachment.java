package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import youtrack.Issue;
import youtrack.IssueAttachment;

import java.io.File;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class AddAttachment extends Command {
    private final Issue issue;
    private final IssueAttachment attachment;

    public AddAttachment(Issue issue, IssueAttachment attachment) {
        this.issue = issue;
        this.attachment = attachment;
    }

    @Override
    public boolean usesAuthorization() {
        return true;
    }

    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public HttpMethodBase commandMethod(String baseHost) {

        PostMethod postMethod = new PostMethod(baseHost + "issue/" + issue.getId() + "/attachment");

        File file = new File(attachment.getUrl());

        try {

            Part[] parts = {
                    new FilePart(file.getName(), file)
            };

            postMethod.setRequestEntity(
                    new MultipartRequestEntity(parts, postMethod.getParams())
            );

            return postMethod;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}

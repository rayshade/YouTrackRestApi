package youtrack.commands;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import youtrack.Issue;
import youtrack.IssueAttachment;
import youtrack.commands.base.AddCommand;
import youtrack.exceptions.CommandExecutionException;

import javax.annotation.Nonnull;
import java.io.File;
/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class AddAttachment extends AddCommand<Issue, IssueAttachment> {
    public AddAttachment(@Nonnull Issue owner) {
        super(owner);
    }
    @Override
    public IssueAttachment getResult() {
        return null;
    }
    @Override
    public void createCommandMethod() throws CommandExecutionException {
        final PostMethod postMethod = new PostMethod(owner.getYouTrack().getHostAddress() + "issue/" + getOwner().getId() + "/attachment");
        final File file = new File(getItem().getUrl());
        try {
            final Part[] parts = {new FilePart(file.getName(), file)};
            postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
        } catch(Exception ex) {
            throw new CommandExecutionException(this, ex);
        }
        method = postMethod;
    }
}
package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
final class GetIssueAttachments extends ListCommand<Issue, IssueAttachment> {
    GetIssueAttachments(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpGet(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/attachment");
    }
}
package youtrack;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
final class AddAttachment extends AddCommand<Issue, IssueAttachment> {
    AddAttachment(Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        final HttpPost postMethod = new HttpPost(owner.getYouTrack().getHostAddress() + "issue/" + getOwner().getId() + "/attachment");
        assert (getItem().getDataStream()) != null;
        postMethod.setEntity(new InputStreamEntity((getItem().getDataStream())));
        return postMethod;
    }
}
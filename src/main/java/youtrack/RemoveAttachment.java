package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
final class RemoveAttachment extends RemoveCommand<Issue, IssueAttachment> {
    RemoveAttachment(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpDelete(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/attachment/" + item.getId());
    }
}

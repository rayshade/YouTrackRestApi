package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
final class RemoveComment extends RemoveCommand<Issue, IssueComment> {
    RemoveComment(@NotNull Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpDelete(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/comment/" + item.getId() + "?permanently=true");
    }
}

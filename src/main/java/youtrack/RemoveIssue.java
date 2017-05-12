package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
final class RemoveIssue extends RemoveCommand<YouTrack, Issue> {
    RemoveIssue(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpDelete(owner.getYouTrack().getHostAddress() + "/issue/" + item.getId());
    }
}
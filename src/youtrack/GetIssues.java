package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
final class GetIssues extends ListCommand<YouTrack, Issue> {
    GetIssues(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpGet(owner.getYouTrack().getHostAddress() + "issue/byproject/" + getOwner().getId());
    }
}
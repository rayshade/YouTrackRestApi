package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
final class GetIssue extends SingleItemCommand<YouTrack, Issue> {
    GetIssue(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        final StringBuilder sb = new StringBuilder();
        sb.append(owner.getYouTrack().getHostAddress()).append("issue/").append(itemId);
        if (!parameters.isEmpty()) {
            sb.append("?");
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(parameters.get(name)).append("&");
            }
        }
        return new HttpGet(sb.toString());
    }
}
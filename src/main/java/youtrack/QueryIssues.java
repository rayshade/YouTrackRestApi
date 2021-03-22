package youtrack;

import org.jetbrains.annotations.NotNull;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
final class QueryIssues extends ListCommand<YouTrack, Issue> {
    QueryIssues(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpGet(owner.getYouTrack().getHostAddress() + "issues/?" + parametersAsQuery());
    }
}
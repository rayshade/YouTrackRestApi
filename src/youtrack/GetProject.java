package youtrack;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by Egor.Malyshev on 26.12.2014.
 */
final class GetProject extends SingleItemCommand<YouTrack, Project> {
    GetProject(YouTrack owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        return new HttpGet(owner.getYouTrack().getHostAddress() + "project/all");
    }
}

package youtrack.commands;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.YouTrack;
import youtrack.commands.base.SingleItemCommand;

import javax.annotation.Nonnull;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends SingleItemCommand<YouTrack, Issue> {
    public GetIssue(@Nonnull YouTrack owner) {
        super(owner);
    }
    @Override
    public void createCommandMethod() {
        final StringBuilder sb = new StringBuilder();
        sb.append(owner.getYouTrack().getHostAddress()).append("issue/").append(itemId);
        if(!parameters.isEmpty()) {
            sb.append("?");
            for(String name : parameters.keySet()) {
                sb.append(name).append("=").append(parameters.get(name)).append("&");
            }
        }
        method = new GetMethod(sb.toString());
    }
}
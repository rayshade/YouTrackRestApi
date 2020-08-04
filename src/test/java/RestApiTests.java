import youtrack.*;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;

import java.io.IOException;

public class RestApiTests {

    public static void main(String[] args) throws AuthenticationErrorException, IOException, CommandExecutionException, CommandNotAvailableException {

        final YouTrack youTrack = YouTrack.getInstance("https://youtrack.jetbrains.com/rest/", true);
        youTrack.setUseTokenAuthorization(true);
        youTrack.setAuthorization("perm:bWVnb3I=.OTItMjQwNw==.scVN5ymttwWm9eEo8h8MzzH7KWC9vK");

        Issue item = youTrack.issues.item("WH-143");

        final IssueComment testing_comment_api = IssueComment.createComment("Testing Comment API");
        testing_comment_api.setVisibility("jetbrains-team");

        item.comments.add(testing_comment_api);
    }

}

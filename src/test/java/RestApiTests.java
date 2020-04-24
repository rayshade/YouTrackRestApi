import youtrack.*;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;

import java.io.IOException;

public class RestApiTests {

    public static void main(String[] args) throws AuthenticationErrorException, IOException, CommandExecutionException, CommandNotAvailableException {

        final YouTrack youTrack = YouTrack.getInstance("https://youtrack.jetbrains.com/rest/", true);
        youTrack.setUseTokenAuthorization(true);
        youTrack.setAuthorization("perm:bWVnb3I=.VGVzdHM=.xoigslREdhjZs78Qj5bQNckmsuQEXt");

        Issue item = youTrack.issues.item("WH-143");

        item.comments.add(IssueComment.createComment("Testing Comment API"));
    }

}

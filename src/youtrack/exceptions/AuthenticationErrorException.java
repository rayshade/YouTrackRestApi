package youtrack.exceptions;
import youtrack.YouTrack;
/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class AuthenticationErrorException extends Exception {
    private final YouTrack youTrack;
    public AuthenticationErrorException(YouTrack youTrack, String userName, String password) {
        super("Cannot login to " + youTrack.getHostAddress() + " as " + userName + " with password " + password);
        this.youTrack = youTrack;
    }
    public YouTrack getYouTrack() {
        return youTrack;
    }
}

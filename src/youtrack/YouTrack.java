package youtrack;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import youtrack.commands.GetProject;
import youtrack.commands.GetProjects;
import youtrack.commands.Login;
import youtrack.commands.base.*;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NotLoggedInException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Egor.Malyshev on 18.12.13.
 */
@SuppressWarnings("unused")
public class YouTrack extends BaseItem {
    private final static Map<String, YouTrack> INSTANCES = new HashMap<String, YouTrack>();
    private final static long INTERVAL = 1800000L;
    public final CommandBasedList<YouTrack, Project> projects;
    private final String hostAddress;
    private String authorization;
    private String userName;
    private String password;
    private long timeout = -1L;
    private YouTrack(@NotNull String hostAddress) {
        this.hostAddress = hostAddress;
        projects = new CommandBasedList<YouTrack, Project>(this, null, null, new GetProjects(this), null, new GetProject(this));
    }
    /**
     * Gets a @link YouTrack instance associated with given URL.
     * <p/>
     * URL must correspond to the base REST API URL of YouTrack server you're connecting to.
     */
    public static YouTrack getInstance(final @NotNull String hostAddress) {
        if(!INSTANCES.containsKey(hostAddress)) INSTANCES.put(hostAddress, new YouTrack(hostAddress));
        return INSTANCES.get(hostAddress);
    }
    @Override
    public String toString() {
        return "YouTrack{" +
                "hostAddress='" + hostAddress + '\'' +
                '}';
    }
    /**
     * Determines if stored YouTrack auth token is still valid for request, and if not, refreshes it.
     */
    private void checkAuthState(final @NotNull Command command) throws NotLoggedInException, AuthenticationErrorException, CommandExecutionException {
        if((Calendar.getInstance().getTimeInMillis() - timeout) > INTERVAL) {
            if(userName != null && password != null) {
                login(userName, password);
            } else {
                throw new NotLoggedInException(command);
            }
        }
    }
    /**
     * Executes a YouTrack command that returns a result that isn't a project item.
     * Typically this is a running command that performs some operation and returns strings or booleans.
     *
     * @return instance of @link CommandResult containing command execution results.
     */
    <O extends BaseItem, R> CommandResultData<R> execute(RunningCommand<O, R> command) throws CommandExecutionException {
        try {
            final HttpClient httpClient = new HttpClient();
            command.createCommandMethod();
            final HttpMethodBase method = command.getMethod();
            if(command.usesAuthorization()) {
                checkAuthState(command);
                method.addRequestHeader("Cookie", authorization);
            }
            httpClient.executeMethod(method);
            final CommandResultData<R> result = new CommandResultData<R>(this, method.getStatusCode(), command.getResult());
            method.releaseConnection();
            return result;
        } catch(CommandExecutionException cee) {
            throw cee;
        } catch(Exception e) {
            throw new CommandExecutionException(command, e);
        }
    }
    /**
     * Executes a YouTrack command that returns nothing. Only execution result itself is available.
     * Typically this is a running command that performs some operation and returns strings or booleans.
     *
     * @return instance of @link CommandResult containing command execution results.
     */
    <O extends BaseItem, R> CommandResultData<R> execute(VoidCommand<O, R> command) throws CommandExecutionException {
        try {
            final HttpClient httpClient = new HttpClient();
            command.createCommandMethod();
            final HttpMethodBase method = command.getMethod();
            if(command.usesAuthorization()) {
                checkAuthState(command);
                method.addRequestHeader("Cookie", authorization);
            }
            httpClient.executeMethod(method);
            final CommandResultData<R> result = new CommandResultData<R>(this, method.getStatusCode(), command.getResult());
            method.releaseConnection();
            return result;
        } catch(CommandExecutionException cee) {
            throw cee;
        } catch(Exception e) {
            throw new CommandExecutionException(command, e);
        }
    }
    /**
     * Executes a YouTrack command that returns a single item result.
     *
     * @return instance of @link CommandResult containing command execution results.
     */
    <O extends BaseItem, R extends BaseItem<O>> CommandResultSingleItem<R> execute(SingleItemCommand<O, R> command) throws CommandExecutionException {
        try {
            final HttpClient httpClient = new HttpClient();
            command.createCommandMethod();
            final HttpMethodBase method = command.getMethod();
            if(command.usesAuthorization()) {
                checkAuthState(command);
                method.addRequestHeader("Cookie", authorization);
            }
            httpClient.executeMethod(method);
            final CommandResultSingleItem<R> result = new CommandResultSingleItem<R>(this, method.getStatusCode(), command.getResult());
            method.releaseConnection();
            return result;
        } catch(CommandExecutionException cee) {
            throw cee;
        } catch(Exception e) {
            throw new CommandExecutionException(command, e);
        }
    }
    /**
     * rjtxyj
     * Executes a YouTrack command that returns multiple results.
     *
     * @return instance of @link CommandResult containing command execution results.
     */
    <O extends BaseItem, R extends BaseItem<O>> CommandResultItemList<R> execute(ListCommand<O, R> command) throws CommandExecutionException {
        try {
            final HttpClient httpClient = new HttpClient();
            command.createCommandMethod();
            final HttpMethodBase method = command.getMethod();
            if(command.usesAuthorization()) {
                checkAuthState(command);
                method.addRequestHeader("Cookie", authorization);
            }
            httpClient.executeMethod(method);
            final CommandResultItemList<R> result = new CommandResultItemList<R>(this, method.getStatusCode(), command.getResult());
            method.releaseConnection();
            return result;
        } catch(CommandExecutionException cee) {
            throw cee;
        } catch(Exception e) {
            throw new CommandExecutionException(command, e);
        }
    }
    public String getHostAddress() {
        return hostAddress;
    }
    /**
     * Tries to authorize current connection and throws AuthenticationErrorException if not successful.
     * <p/>
     * Retrieved token is stored for later use with all commands that need authentication.
     */
    public void login(@NotNull String userName, @NotNull String password) throws AuthenticationErrorException, CommandExecutionException {
        final Login login = new Login(this);
        login.addParameter("login", userName);
        login.addParameter("password", password);
        final CommandResultData<String> result = execute(login);
        if(result.success()) {
            authorization = result.getResult();
            this.userName = userName;
            this.password = password;
            timeout = Calendar.getInstance().getTimeInMillis();
        } else throw new AuthenticationErrorException(this, userName, password.replaceAll("\\.", "*"));
    }
    public String getAuthorization() {
        return authorization;
    }
    public void setAuthorization(@NotNull String authorization) {
        this.authorization = authorization;
    }
    @Override
    public YouTrack getYouTrack() {
        return this;
    }
}
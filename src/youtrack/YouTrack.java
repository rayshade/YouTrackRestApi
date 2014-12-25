package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import youtrack.commands.Command;
import youtrack.commands.CommandResult;
import youtrack.commands.GetProjects;
import youtrack.commands.Login;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Egor.Malyshev on 18.12.13.
 */
public class YouTrack extends BaseItem {

    private final static Map<String, YouTrack> INSTANCES = new HashMap<String, YouTrack>();
    private final String hostAddress;
    private String authorization;

    private YouTrack(@NotNull String hostAddress) {
        this.hostAddress = hostAddress;
    }

    /**
     * Gets a @link YouTrack instance associated with given URL.
     * <p/>
     * URL must correspond to the base REST API URL of YouTrack server you're connecting to.
     */

    public static YouTrack getInstance(final @NotNull String hostAddress) {
        if (!INSTANCES.containsKey(hostAddress)) INSTANCES.put(hostAddress, new YouTrack(hostAddress));
        return INSTANCES.get(hostAddress);
    }

    /**
     * Executes a YouTrack command described by an object that extends @link Command class.
     *
     * @return instance of @link CommandResult containing command execution results.
     */

    <O extends BaseItem, R> CommandResult<R> execute(Command<O, R> command) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        final HttpClient httpClient = new HttpClient();
        final HttpMethodBase method = command.commandMethod(hostAddress);
        if (command.usesAuthorization()) {
            method.addRequestHeader("Cookie", authorization);
        }
        httpClient.executeMethod(method);
        final CommandResult<R> result = new CommandResult<R>(command.getResult(), method.getStatusCode());
        method.releaseConnection();
        return result;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    /**
     * Retrieve a list of projects from current connection.
     *
     * @return list of @link Project instances or null if there was an error.
     */

    public List<Project> projects() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        final CommandResult<List<Project>> result = execute(new GetProjects(this));
        final List<Project> projectList = result.getData();
        if (projectList != null) {
            for (final Project project : projectList) {
                project.setYouTrack(this);
            }
            return projectList;
        } else return null;
    }

    /**
     * Tries to authorize current connection and returns true if successful, false otherwise.
     * <p/>
     * Retrieved token is stored for later use with all commands that need authentication.
     */

    public void login(@NotNull String userName, @NotNull String password) throws AuthenticationErrorException, IOException, NoSuchIssueFieldException, CommandExecutionException {
        final Login login = new Login(this);
        final HashMap<String, String> arguments = new HashMap<String, String>();
        arguments.put("login", userName);
        arguments.put("password", password);
        login.setArguments(arguments);
        final CommandResult<String> result = execute(login);
        if (result.success()) {
            authorization = result.getData();
        } else throw new AuthenticationErrorException(this, userName, password.replaceAll(".", "*"));
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(@NotNull String authorization) {
        this.authorization = authorization;
    }

    /**
     * Retrieves a single project by id.
     *
     * @return @link Project instance or null if there was an error.
     */

    public Project project(final @Nullable String id) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        if (id == null) return null;
        final List<Project> projects = this.projects();
        if (projects != null) {
            for (final Project project : projects) {
                if (id.equals(project.getId())) return project;
            }
        }
        return null;
    }

    @Override
    public YouTrack getYouTrack() {
        return this;
    }
}
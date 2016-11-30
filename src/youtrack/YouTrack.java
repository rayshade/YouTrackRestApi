package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NotLoggedInException;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Egor.Malyshev on 18.12.13.
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class YouTrack extends BaseItem {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private final static Map<String, YouTrack> INSTANCES = new HashMap<String, YouTrack>();
    private final static long INTERVAL = 1800000L;
    public final CommandBasedList<YouTrack, Issue> issues;
    public final CommandBasedList<YouTrack, Project> projects;
    private final String hostAddress;
    private String authorization;
    private String userName;
    private String password;
    private long timeout = -1L;
    private final boolean trustAllMode;

    public boolean isTrustAllMode() {
        return trustAllMode;
    }

    private YouTrack(@NotNull String hostAddress) {
        this(hostAddress, false);
    }

    private YouTrack(@NotNull String hostAddress, boolean trustAllMode) {
        this.hostAddress = hostAddress;
        this.trustAllMode = trustAllMode;
        projects = new CommandBasedList<YouTrack, Project>(this, null, null, new GetProjects(this), null, new GetProject(this));
        issues = new CommandBasedList<YouTrack, Issue>(this, new AddIssue(this), new RemoveIssue(this), null, new QueryIssues(this), new GetIssue(this));
    }

    /**
     * Gets a @link YouTrack instance associated with given URL and doesn't trust self-signed SSL certificates.
     * <p/>
     * URL must correspond to the base REST API URL of YouTrack server you're connecting to.
     */
    public static YouTrack getInstance(final @NotNull String hostAddress) {
        return getInstance(hostAddress, false);
    }

    /**
     * Gets a @link YouTrack instance associated with given URL and lets you choose if you want to trust self-signed certificates.
     * <p/>
     * URL must correspond to the base REST API URL of YouTrack server you're connecting to.
     */
    public static YouTrack getInstance(final @NotNull String hostAddress, boolean trustAllMode) {
        if (!INSTANCES.containsKey(hostAddress)) INSTANCES.put(hostAddress, new YouTrack(hostAddress, trustAllMode));
        return INSTANCES.get(hostAddress);
    }

    @Override
    public String toString() {
        return "YouTrack{" +
                "hostAddress='" + hostAddress + '\'' +
                '}';
    }

    /**
     * Gets an http client to perform requests.
     */
    public CloseableHttpClient getHttpClient() {
        if (trustAllMode)
            try {
                final SSLContextBuilder builder = new SSLContextBuilder();
                builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build()) {
                    /*
                    This is patched to avoid "Could not generate DH keypair" error with JDK < 1.8
                    */
                    protected void prepareSocket(SSLSocket socket) throws IOException {
                        final String[] enabledCipherSuites = socket.getEnabledCipherSuites();
                        final List<String> list = new ArrayList<String>(Arrays.asList(enabledCipherSuites));
                        list.remove("TLS_DHE_RSA_WITH_AES_128_CBC_SHA");
                        list.remove("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA");
                        list.remove("TLS_DHE_RSA_WITH_AES_256_CBC_SHA");
                        socket.setEnabledCipherSuites(list.toArray(new String[list.size()]));
                    }
                };
                return HttpClients.custom().setSSLSocketFactory(sslsf).build();
            } catch (Exception e) {
                LOG.warning("Could not create all-trusting connection, using default: " + e.getMessage());
            }
        return HttpClients.createSystem();
    }

    private void checkAuthState(final @NotNull Command command) throws NotLoggedInException, AuthenticationErrorException, CommandExecutionException, IOException {
        if ((Calendar.getInstance().getTimeInMillis() - timeout) > INTERVAL) {
            if (userName != null && password != null) {
                login(userName, password);
            } else {
                throw new NotLoggedInException(command);
            }
        }
    }

    /**
     * Executes a YouTrack command and returns a result.
     *
     * @return instance of @link CommandResult containing command execution results.
     */
    <O extends BaseItem, R> CommandResult<R> execute(Command<O, R> command) throws CommandExecutionException, IOException {
        final CloseableHttpClient httpClient = getHttpClient();
        try {
            if (command.usesAuthorization()) {
                checkAuthState(command);
            }
            command.run(httpClient, authorization);
            final CommandResult<R> result = command.getResult();
            if (result.getException() != null) throw result.getException();
            if (result.getError() != null) throw new CommandExecutionException(command, result.getError());
            return result;
        } catch (CommandExecutionException cee) {
            throw cee;
        } catch (Exception e) {
            throw new CommandExecutionException(command, e);
        } finally {
            command.close();
            httpClient.close();
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
    public void login(@NotNull String userName, @NotNull String password) throws AuthenticationErrorException, CommandExecutionException, IOException {
        final Login login = new Login(this);
        login.setParameter("login", userName);
        login.setParameter("password", password);
        final CommandResult<String> result = execute(login);
        if (result.success()) {
            authorization = result.getResult();
            this.userName = userName;
            this.password = password;
            timeout = Calendar.getInstance().getTimeInMillis();
        } else throw new AuthenticationErrorException(this, userName, password.replaceAll(".", "*"));
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
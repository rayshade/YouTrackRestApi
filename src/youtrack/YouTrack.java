package youtrack;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import youtrack.commands.Command;
import youtrack.commands.GetProjects;
import youtrack.commands.Login;
import youtrack.commands.results.Result;
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
public class YouTrack {

	private final static Map<String, YouTrack> INSTANCES = new HashMap<String, YouTrack>();
	private final String hostAddress;

	private String authorization;

	private YouTrack(String hostAddress) {

		this.hostAddress = hostAddress;
	}

	/**
	 * Gets a @link YouTrack instance associated with given URL.
	 * <p/>
	 * URL must correspond to the base REST API URL of YouTrack server you're connecting to.
	 */

	public static YouTrack getInstance(String hostAddress) {

		if (!INSTANCES.containsKey(hostAddress)) INSTANCES.put(hostAddress, new YouTrack(hostAddress));

		return INSTANCES.get(hostAddress);
	}

	/**
	 * Executes a YouTrack command described by an object that extends @link Command class.
	 *
	 * @return instance of @link Result containing command execution results.
	 */

	Result execute(Command command) throws IOException, NoSuchIssueFieldException, CommandExecutionException {

		HttpClient httpClient = new HttpClient();

		HttpMethodBase method = command.commandMethod(hostAddress);

		if (command.usesAuthorization()) {
			method.addRequestHeader("Cookie", authorization);
		}

		httpClient.executeMethod(method);

		Result result = new Result(command.getResult(), method.getStatusCode());

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

		Result result = execute(new GetProjects());

		List<Project> projectList = (List<Project>) result.getData();

		if (projectList != null) {

			for (Project project : projectList) {
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

	public void login(String userName, String password) throws AuthenticationErrorException, IOException, NoSuchIssueFieldException, CommandExecutionException {

		Result result = execute(new Login(userName, password));

		if (result.success()) {

			authorization = (String) result.getData();

		} else throw new AuthenticationErrorException(this, userName, password.replaceAll(".", "*"));

	}

	/**
	 * Retrieves a single project by id.
	 *
	 * @return @link Project instance or null if there was an error.
	 */

	public Project project(String id) throws IOException, NoSuchIssueFieldException, CommandExecutionException {

		List<Project> projects = this.projects();

		if (projects != null) {

			for (Project project : projects) {
				if (project.getId().equals(id)) return project;
			}

		}

		return null;

	}

}
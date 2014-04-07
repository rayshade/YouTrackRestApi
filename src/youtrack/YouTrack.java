package youtrack;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import youtrack.commands.AddAttachment;
import youtrack.commands.Command;
import youtrack.commands.GetProjects;
import youtrack.commands.Login;
import youtrack.commands.results.Result;
import youtrack.util.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Egor.Malyshev on 18.12.13.
 */
public class YouTrack {

	private final static Map<String, YouTrack> INSTANCES = new HashMap<String, YouTrack>();
	private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
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

	Result execute(Command command) {

		Result result = null;

		try {

			String commandData = "";

			URL url;
			HttpURLConnection httpURLConnection;

			if (command instanceof AddAttachment) {

				AddAttachment addAttachment = (AddAttachment) command;

				url = new URL(hostAddress + command.getUrl());

				PostMethod filePost = new PostMethod(url.toString());

				File file = new File(addAttachment.getAttachment().getUrl());

				Part[] parts = {
						new FilePart(file.getName(), file)
				};

				filePost.setRequestEntity(
						new MultipartRequestEntity(parts, filePost.getParams())
				);

				filePost.addRequestHeader("Cookie", authorization);

				HttpClient htc = new HttpClient();

				htc.executeMethod(filePost);

				result = new Result(null, filePost.getStatusCode());

				filePost.releaseConnection();

			} else {

				if (command.getParams() != null) {
					for (String commandKey : command.getParams().keySet()) {

						commandData += commandKey + "=" + Service.encode(command.getParams().get(commandKey)) + "&";
					}
					commandData = commandData.substring(0, commandData.length() - 1);
				}


				if (command.getRequestMethod().equals("POST")) {

					url = new URL(hostAddress + command.getUrl());

				} else {

					url = new URL(hostAddress + command.getUrl() + ((commandData.length() > 0) ? "?" : "") + commandData);
				}

				httpURLConnection = (HttpURLConnection) getUrlConnection(url);

				if (command.getRequestMethod().equals("POST") || command.getRequestMethod().equals("PUT"))
					httpURLConnection.setDoOutput(true);

				httpURLConnection.setRequestMethod(command.getRequestMethod());

				if (command.usesAuthorization()) {
					httpURLConnection.setRequestProperty("Cookie", authorization);
				}

				if (command.getRequestMethod().equals("POST") || command.getRequestMethod().equals("PUT")) {
					httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
					if (command.getParams() != null) {
						OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream(), Service.ENC);
						writer.write(commandData);
						writer.flush();
						writer.close();
					}
				}
				result = new Result(command.getResult(httpURLConnection), httpURLConnection.getResponseCode());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Helper method to get an instance of URLConnection with custom timeouts.
	 *
	 * @param remoteUrl URL to which connection is requested.
	 * @return URLConnection instance.
	 * @throws IOException
	 */
	private URLConnection getUrlConnection(URL remoteUrl) throws IOException {
		URLConnection urlConnection = remoteUrl.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(15000);
		return urlConnection;
	}


	/**
	 * Retrieve a list of projects from current connection.
	 *
	 * @return list of @link Project instances or null if there was an error.
	 */

	public List<Project> listProjects() {

		Result result = execute(new GetProjects());

		ProjectList projectList = (ProjectList) result.getData();

		if (projectList != null) {

			projectList.setYouTrack(this);
			return projectList.getProjectList();

		} else return null;

	}

	/**
	 * Tries to authorize current connection and returns true if successful, false otherwise.
	 * <p/>
	 * Retrieved token is stored for later use with all commands that need authentication.
	 */

	public boolean doLogin(String userName, String password) {

		Result result = execute(new Login(userName, password));

		if (result.success()) {

			authorization = (String) result.getData();

			return true;

		} else return false;

	}

	/**
	 * Retrieves a single project by id.
	 *
	 * @return @link Project instance or null if there was an error.
	 */

	public Project project(String id) {

		Result result = execute(new GetProjects());

		ProjectList projectList = (ProjectList) result.getData();

		if (projectList != null) {


			Project project = projectList.getProject(id);

			if (project != null) {
				project.setYouTrack(this);
				return project;
			}

		}

		return null;

	}

}
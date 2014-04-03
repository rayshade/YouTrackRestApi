package youtrack.commands;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class AddIssue extends Command {
	private final String projectId;
	private final String summary;
	private final String description;

	public AddIssue(String projectId, String summary, String description) {

		this.projectId = projectId;
		this.summary = summary;
		this.description = description;
	}

	@Override
	public String getUrl() {
		return "issue";
	}

	@Override
	public Map<String, String> getParams() {

		Map<String, String> result = new HashMap<String, String>();
		result.put("project", projectId);
		result.put("summary", summary);
		result.put("description", description);

		return result;
	}

	@Override
	public String getRequestMethod() {
		return "PUT";
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult(HttpURLConnection httpURLConnection) {
		try {
			getResponse(httpURLConnection);
			String[] locations = httpURLConnection.getHeaderField("Location").split("/");
			return locations[locations.length - 1];
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

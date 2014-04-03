package youtrack.commands;

import youtrack.Issue;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class ModifyIssue extends Command {
	private final Issue issue;
	private final String summary;
	private final String description;

	public ModifyIssue(Issue issue, String summary, String description) {
		this.issue = issue;
		this.summary = summary;
		this.description = description;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId();
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("summary", ((summary == null) ? issue.getSummary() : summary));
		result.put("description", ((description == null) ? issue.getDescription() : description));
		return result;
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult(HttpURLConnection httpURLConnection) {
		return null;
	}
}
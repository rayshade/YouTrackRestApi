package youtrack.commands;

import youtrack.Issue;
import youtrack.IssueLink;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveIssueLink extends Command {
	private final Issue issue;
	private final IssueLink link;

	public RemoveIssueLink(Issue issue, IssueLink link) {
		this.issue = issue;
		this.link = link;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/execute";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();

		result.put("command", "remove " + link.getTypeOutward() + " " + link.getTarget());

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

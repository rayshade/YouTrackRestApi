package youtrack.commands;

import youtrack.Issue;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class ChangeIssueVotes extends Command {
	private final Issue issue;
	private final boolean vote;

	public ChangeIssueVotes(Issue issue, boolean vote) {
		this.issue = issue;
		this.vote = vote;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/execute";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("command", ((vote) ? "vote" : "unvote"));
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

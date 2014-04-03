package youtrack.commands;

import youtrack.Issue;
import youtrack.IssueLink;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class AddIssueLink extends Command {
	private final Issue issue;
	private final IssueLink issueLink;

	public AddIssueLink(Issue issue, IssueLink issueLink) {

		this.issue = issue;
		this.issueLink = issueLink;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/execute";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();

		result.put("command", issueLink.getTypeName() + issueLink.getTarget());
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

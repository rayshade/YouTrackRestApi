package youtrack.commands;

import youtrack.Issue;
import youtrack.IssueComment;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class RemoveComment extends Command {
	private final Issue issue;
	private final String commentId;

	public RemoveComment(Issue issue, IssueComment comment) {
		this.issue = issue;
		commentId = comment.getId();
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/comment/" + commentId;
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("permanently", String.valueOf(true));
		return result;
	}

	@Override
	public String getRequestMethod() {
		return "DELETE";
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

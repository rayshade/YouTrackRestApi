package youtrack.commands;

import youtrack.Issue;
import youtrack.IssueComment;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class AddComment extends Command {
	private final String comment;
	private final Issue issue;

	public AddComment(Issue issue, IssueComment comment) {
		this.comment = comment.getText();
		this.issue = issue;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/execute";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("comment", comment);
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
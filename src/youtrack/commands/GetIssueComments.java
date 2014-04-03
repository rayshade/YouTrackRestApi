package youtrack.commands;

import youtrack.CommentList;
import youtrack.Issue;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueComments extends Command {
	private final Issue issue;

	public GetIssueComments(Issue issue) {
		this.issue = issue;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/comment";
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override

	public Object getResult(HttpURLConnection httpURLConnection) {
		try {
			String response = getResponse(httpURLConnection);

			CommentList commentList = (CommentList) objectFromXml(response);

			return commentList.getComments();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
package youtrack.commands;

import youtrack.Issue;
import youtrack.IssueAttachment;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class RemoveAttachment extends Command {
	private final Issue issue;
	private final IssueAttachment attachment;

	public RemoveAttachment(Issue issue, IssueAttachment attachment) {
		this.issue = issue;
		this.attachment = attachment;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/attachment/" + attachment.getId();
	}

	@Override
	public Map<String, String> getParams() {
		return null;
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

package youtrack.commands;

import youtrack.AttachmentList;
import youtrack.Issue;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class GetIssueAttachments extends Command {
	private final Issue issue;

	public GetIssueAttachments(Issue issue) {
		this.issue = issue;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/attachment";
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
			AttachmentList attachmentList = (AttachmentList) objectFromXml(getResponse(httpURLConnection));

			return attachmentList.getAttachments();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

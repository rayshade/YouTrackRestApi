package youtrack.commands;

import youtrack.Issue;
import youtrack.LinkList;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueLinks extends Command {
	private final Issue issue;

	public GetIssueLinks(Issue issue) {
		this.issue = issue;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/link";
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

			LinkList linkList = (LinkList) objectFromXml(getResponse(httpURLConnection));
			return linkList.getLinks();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

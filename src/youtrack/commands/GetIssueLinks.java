package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.LinkList;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueLinks extends Command {
	private final Issue issue;

	public GetIssueLinks(Issue issue) {
		this.issue = issue;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {
		try {

			LinkList linkList = (LinkList) objectFromXml(method.getResponseBodyAsString());
			return linkList.getLinks();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "issue/" + issue.getId() + "/link");
		return method;
	}
}
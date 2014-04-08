package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueLink;
import youtrack.LinkList;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueLinks extends Command<List<IssueLink>> {
	private final Issue issue;

	public GetIssueLinks(Issue issue) {
		this.issue = issue;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public List<IssueLink> getResult() throws CommandExecutionException {
		try {

			LinkList linkList = (LinkList) objectFromXml(method.getResponseBodyAsString());
			return linkList.getItems();

		} catch (Exception e) {
			throw new CommandExecutionException(this, e);
		}
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "issue/" + issue.getId() + "/link");
		return method;
	}
}
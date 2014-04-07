package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.IssueLink;

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
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {
		return null;
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		PostMethod postMethod = new PostMethod(baseHost + "issue/" + issue.getId() + "/execute");
		postMethod.addParameter("command", issueLink.getTypeName() + issueLink.getTarget());
		return postMethod;
	}
}

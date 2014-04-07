package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class ChangeIssueVotes extends Command {
	private final Issue issue;
	private final boolean vote;

	public ChangeIssueVotes(Issue issue, boolean vote) {
		this.issue = issue;
		this.vote = vote;
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
		postMethod.addParameter("command", ((vote) ? "vote" : "unvote"));
		return postMethod;
	}
}

package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import youtrack.Issue;
import youtrack.IssueAttachment;

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
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {
		return null;
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {

		return new DeleteMethod(baseHost + "issue/" + issue.getId() + "/attachment/" + attachment.getId());

	}
}

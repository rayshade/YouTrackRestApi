package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class ModifyIssue extends Command {
	private final Issue issue;
	private final String summary;
	private final String description;

	public ModifyIssue(Issue issue, String summary, String description) {
		this.issue = issue;
		this.summary = summary;
		this.description = description;
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
	public HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
		PostMethod postMethod = new PostMethod(baseHost + "issue/" + issue.getId());

		postMethod.setRequestBody(new NameValuePair[]{
				new NameValuePair("summary", ((summary == null) ? issue.getSummary() : summary)),
				new NameValuePair("description", ((description == null) ? issue.getDescription() : description))

		});

		return postMethod;
	}
}
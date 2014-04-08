package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import youtrack.Issue;
import youtrack.Project;
import youtrack.exceptions.CommandExecutionException;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class AddIssue extends Command<String> {
	private final Project project;
	private final Issue issue;

	public AddIssue(Project project, Issue issue) {

		this.project = project;
		this.issue = issue;

	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public String getResult() throws CommandExecutionException {
		try {
			String[] locations = method.getResponseHeader("Location").getValue().split("/");
			return locations[locations.length - 1];
		} catch (Exception e) {
			throw new CommandExecutionException(this, e);
		}
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new PutMethod(baseHost + "issue");

		HttpMethodParams params = new HttpMethodParams();
		params.setParameter("project", project.getId());

		try {

			params.setParameter("summary", issue.getSummary());
			params.setParameter("description", issue.getDescription());

		} catch (Exception e) {
			e.printStackTrace();
		}

		method.setParams(params);

		return method;

	}
}

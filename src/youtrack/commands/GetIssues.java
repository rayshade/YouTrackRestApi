package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueProjectList;
import youtrack.Project;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetIssues extends Command<List<Issue>> {


	private final Project project;

	public GetIssues(Project project) {

		this.project = project;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public List<Issue> getResult() throws CommandExecutionException {

		try {
			IssueProjectList itemList = (IssueProjectList) objectFromXml(method.getResponseBodyAsString());

			return itemList.getItems();

		} catch (Exception ex) {
			throw new CommandExecutionException(this, ex);
		}

	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "issue/byproject/" + project.getId());
/*
		HttpMethodParams params = new HttpMethodParams();
		params.setParameter("filter", query);
		method.setParams(params);
*/
		return method;
	}
}

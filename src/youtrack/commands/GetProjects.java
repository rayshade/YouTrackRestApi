package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Project;
import youtrack.ProjectList;
import youtrack.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetProjects extends Command<List<Project>> {

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public List<Project> getResult() throws CommandExecutionException {

		try {

			ProjectList projectList = (ProjectList) objectFromXml(method.getResponseBodyAsString());

			return projectList.getItems();

		} catch (Exception e) {
			throw new CommandExecutionException(this, e);
		}
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "project/all");
		return method;
	}
}

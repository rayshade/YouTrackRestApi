package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class AddIssue extends Command {
	private final String projectId;
	private final String summary;
	private final String description;

	public AddIssue(String projectId, String summary, String description) {

		this.projectId = projectId;
		this.summary = summary;
		this.description = description;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {
		try {
			String[] locations = method.getResponseHeader("Location").getValue().split("/");
			return locations[locations.length - 1];
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new PutMethod(baseHost + "issue");

		HttpMethodParams params = new HttpMethodParams();
		params.setParameter("project", projectId);
		params.setParameter("summary", summary);
		params.setParameter("description", description);

		method.setParams(params);

		return method;

	}
}

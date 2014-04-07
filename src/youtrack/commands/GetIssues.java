package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetIssues extends Command {
	private final String query;

	public GetIssues(String query) {

		this.query = query;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {

		try {

			return objectFromXml(method.getResponseBodyAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "issue");
		HttpMethodParams params = new HttpMethodParams();
		params.setParameter("filter", query);
		method.setParams(params);
		return method;
	}
}

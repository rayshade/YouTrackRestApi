package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends Command {
	private final String id;

	public GetIssue(String id) {
		this.id = id;
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

		method = new GetMethod(baseHost + "issue/" + id);

		return method;

	}

}
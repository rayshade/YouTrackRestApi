package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetProjects extends Command {

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {

		try {

			return objectFromXml(method.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "project/all");
		return method;
	}
}

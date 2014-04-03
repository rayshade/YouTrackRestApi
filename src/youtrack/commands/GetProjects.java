package youtrack.commands;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetProjects extends Command {
	@Override
	public String getUrl() {
		return "project/all";
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult(HttpURLConnection httpURLConnection) {

		try {

			String response = getResponse(httpURLConnection);

			return objectFromXml(response);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}

package youtrack.commands;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue extends Command {
	private final String id;

	public GetIssue(String id) {
		this.id = id;
	}

	@Override
	public String getUrl() {
		return "issue/" + id;
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

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
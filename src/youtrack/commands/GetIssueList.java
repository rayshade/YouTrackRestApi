package youtrack.commands;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetIssueList extends Command {
	private final String query;

	public GetIssueList(String query) {

		this.query = query;
	}

	@Override
	public String getUrl() {
		return "issue";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("filter", query);
		return result;
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

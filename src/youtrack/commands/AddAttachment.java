package youtrack.commands;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class AddAttachment extends Command {
	@Override
	public String getUrl() {
		return null;
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	@Override
	public String getRequestMethod() {
		return null;
	}

	@Override
	public boolean usesAuthorization() {
		return false;
	}

	@Override
	public Object getResult(HttpURLConnection httpURLConnection) {
		return null;
	}
}

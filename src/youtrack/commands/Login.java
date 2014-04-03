package youtrack.commands;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class Login extends Command {
	private final String userName;
	private final String password;

	public Login(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String getUrl() {
		return "user/login";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("login", userName);
		result.put("password", password);
		return result;
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public boolean usesAuthorization() {
		return false;
	}

	@Override
	public Object getResult(HttpURLConnection httpURLConnection) {
		return httpURLConnection.getHeaderField("Set-Cookie");
	}
}
package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class Login extends Command<String> {
	private final String userName;
	private final String password;

	public Login(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public boolean usesAuthorization() {
		return false;
	}

	@Override
	public String getResult() {
		return method.getResponseHeader("Set-Cookie").getValue();
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {

		PostMethod postMethod = new PostMethod(baseHost + "user/login");

		postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("login", userName),
						new NameValuePair("password", password)}
		);

		method = postMethod;

		return method;
	}
}
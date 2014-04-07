package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class RemoveIssueTag extends Command {
	@Override
	public boolean usesAuthorization() {
		return false;
	}

	@Override
	public Object getResult() {
		return null;
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		return null;
	}
}

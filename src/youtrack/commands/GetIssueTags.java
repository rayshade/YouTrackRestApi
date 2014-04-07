package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.TagList;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class GetIssueTags extends Command {
	private final Issue issue;

	public GetIssueTags(Issue issue) {
		this.issue = issue;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {

		try {

			TagList tagList = (TagList) objectFromXml(method.getResponseBodyAsString());

			return tagList.getTags();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		method = new GetMethod(baseHost + "issue/" + issue.getId() + "/tags/");
		return method;
	}
}
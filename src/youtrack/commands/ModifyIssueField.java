package youtrack.commands;


import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import youtrack.Issue;
import youtrack.issue.fields.IssueField;
import youtrack.issue.fields.values.BaseIssueFieldValue;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class ModifyIssueField extends Command {
	private final Issue issue;
	private final IssueField target;
	private final BaseIssueFieldValue newVaule;

	public ModifyIssueField(Issue issue, IssueField target, BaseIssueFieldValue newVaule) {

		this.issue = issue;
		this.target = target;
		this.newVaule = newVaule;
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult() {

		try {

			if (method.getStatusCode() == 200) {

				return null;

			} else {

				return objectFromXml(method.getResponseBodyAsString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HttpMethodBase commandMethod(String baseHost) {
		PostMethod postMethod = new PostMethod(baseHost + "issue/" + issue.getId() + "/execute");

		postMethod.setRequestBody(new NameValuePair[]{
				new NameValuePair("command", target.getName() + " " + newVaule.getValue())

		});

		method = postMethod;

		return method;
	}
}

package youtrack.commands;

import youtrack.Issue;
import youtrack.issue.fields.IssueField;
import youtrack.issue.fields.values.BaseIssueFieldValue;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

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
	public String getUrl() {
		return "issue/" + issue.getId() + "/execute";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();

		result.put("command", target.getName() + " " + newVaule.getValue());

		return result;
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public Object getResult(HttpURLConnection httpURLConnection) {

		try {
			String response = getResponse(httpURLConnection);

			if (httpURLConnection.getResponseCode() == 200) {

				return null;

			} else {

				return objectFromXml(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

package youtrack.commands;

import youtrack.Issue;
import youtrack.IssueAttachment;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
public class AddAttachment extends Command {
	private final Issue issue;
	private final IssueAttachment attachment;

	public AddAttachment(Issue issue, IssueAttachment attachment) {
		this.issue = issue;
		this.attachment = attachment;
	}

	@Override
	public String getUrl() {
		return "issue/" + issue.getId() + "/attachment";
	}

	public IssueAttachment getAttachment() {
		return attachment;
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();


		try {


			RandomAccessFile f = new RandomAccessFile(attachment.getUrl(), "rw");
			byte[] data = new byte[(int) f.length()];

			f.read(data);

			String base64data = DatatypeConverter.printBase64Binary(data);



		} catch (IOException e) {
			e.printStackTrace();
		}


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
		return null;
	}
}

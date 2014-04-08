package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
@XmlRootElement(name = "fileUrl")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueAttachment extends Item {

	@XmlAttribute(name = "url")
	private String url;

	@XmlAttribute(name = "name")
	private String name;

	IssueAttachment() {
	}

	/**
	 * Creates IssueAttachment when you need to add a new attachment to an issue.
	 *
	 * @param fileName local file to map attachment to.
	 * @return a new IssueAttachment instance mapped to a local file.
	 */

	public static IssueAttachment createAttachment(@NotNull String fileName) {
		IssueAttachment issueAttachment = new IssueAttachment();
		issueAttachment.url = fileName;
		issueAttachment.wrapper = true;
		return issueAttachment;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "IssueAttachment{" +
				"url='" + url + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	public String getId() {

		Pattern extractor = Pattern.compile("\\?file=([0-9\\-]+)", Pattern.UNICODE_CASE);
		Matcher matcher = extractor.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		} else return null;

	}

	/**
	 * Saves attachment to a local folder under its original file name.
	 *
	 * @param path local path to save attachment to.
	 */

	public void saveTo(@NotNull String path) throws IOException {

		HttpClient client = new HttpClient();

		GetMethod get = new GetMethod(this.url);

		client.executeMethod(get);

		InputStream in = get.getResponseBodyAsStream();

		if (!path.endsWith("/")) path += "/";

		FileOutputStream out = new FileOutputStream(new File(path + this.name));

		byte[] b = new byte[1024];

		int len;

		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}

		in.close();
		out.close();
		get.releaseConnection();
	}
}
package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
@XmlRootElement(name = "fileUrl")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueAttachment extends BaseItem<Issue> {
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

    /**
     * Saves attachment to a local folder under its original file name.
     *
     * @param path local path to save attachment to.
     */
    public boolean saveTo(@NotNull String path) throws IOException {
        final CloseableHttpClient client = youTrack.getHttpClient();
        final HttpGet get = new HttpGet(this.url);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            CloseableHttpResponse response = client.execute(get);
            if (!path.endsWith(File.separator)) path += File.separator;
            out = new FileOutputStream(new File(path + this.name));
            in = response.getEntity().getContent();
            IOUtils.copy(in, out);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
}
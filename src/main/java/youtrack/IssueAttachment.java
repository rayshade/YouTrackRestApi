package youtrack;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.annotation.Nonnull;
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
    public static IssueAttachment createAttachment(@Nonnull String fileName) {
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
    public void saveTo(@Nonnull String path) throws IOException {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(this.url);
        client.executeMethod(get);
        InputStream in = get.getResponseBodyAsStream();
        if(!path.endsWith(File.separator)) path += File.separator;
        FileOutputStream out = new FileOutputStream(new File(path + this.name));
        byte[] b = new byte[1024];
        int len;
        while((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
        get.releaseConnection();
    }
}
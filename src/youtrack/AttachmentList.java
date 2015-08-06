package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 03.04.2014.
 */
@XmlRootElement(name = "fileUrls")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttachmentList extends BaseItemList<IssueAttachment> {
    @XmlElement(name = "fileUrl")
    private List<IssueAttachment> attachments;
    AttachmentList() {
    }
    @Override
    public List<IssueAttachment> getItems() {
        return attachments;
    }
}
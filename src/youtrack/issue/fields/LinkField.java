package youtrack.issue.fields;

import youtrack.issue.fields.values.BaseIssueFieldValue;
import youtrack.issue.fields.values.LinkFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class LinkField extends IssueField {
    @XmlElement(name = "value")
    private List<LinkFieldValue> links;

    public LinkField() {
    }

    public List<LinkFieldValue> getLinks() {
        return links;
    }

    @Override
    public LinkFieldValue getValue() {
        return null;
    }

    @Override
    public void setValue(BaseIssueFieldValue value) {

    }

}
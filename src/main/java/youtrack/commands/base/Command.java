package youtrack.commands.base;
import org.apache.commons.httpclient.HttpMethodBase;
import youtrack.*;
import youtrack.Error;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.issue.fields.AttachmentField;
import youtrack.issue.fields.BaseIssueField;
import youtrack.issue.fields.CustomField;
import youtrack.issue.fields.CustomFieldValue;
import youtrack.issue.fields.IssueField;
import youtrack.issue.fields.IssueListField;
import youtrack.issue.fields.LinkField;
import youtrack.issue.fields.MultiUserField;
import youtrack.issue.fields.SingleField;
import youtrack.issue.fields.values.AttachmentFieldValue;
import youtrack.issue.fields.values.BaseIssueFieldValue;
import youtrack.issue.fields.values.IssueFieldValue;
import youtrack.issue.fields.values.LinkFieldValue;
import youtrack.issue.fields.values.MultiUserFieldValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by egor.malyshev on 31.03.2014.
 */
public abstract class Command<O extends BaseItem, R> {
    protected final O owner;
    protected HttpMethodBase method;
    protected Map<String, String> parameters = new HashMap<String, String>();
    public Command(final @Nonnull O owner) {
        this.owner = owner;
    }
    @Override
    public String toString() {
        return "Command " + this.getClass().getSimpleName() + "{" +
                "owner=" + owner +
                '}';
    }
    @Nonnull
    public O getOwner() {
        return owner;
    }
    public boolean usesAuthorization() {
        return true;
    }
    @Nullable
    public abstract R getResult() throws CommandExecutionException, AuthenticationErrorException;
    /**
     * Helper method to deserealize XML to objects. Used to interpret XML response received from YouTrack.
     *
     * @param xmlString Raw XML code.
     * @return Instance of an object.
     */
    protected Object objectFromXml(final @Nonnull String xmlString) throws Exception {
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        Object result;
        final XMLStreamReader streamReader = new HackedReader(xmlInputFactory.createXMLStreamReader(new StringReader(xmlString)));
        final JAXBContext jaxbContext = JAXBContext.newInstance(AttachmentField.class,
                BaseIssueField.class,
                CustomField.class,
                CustomFieldValue.class,
                IssueField.class,
                IssueListField.class,
                LinkField.class,
                MultiUserField.class,
                SingleField.class, AttachmentFieldValue.class,
                BaseIssueFieldValue.class,
                IssueFieldValue.class,
                LinkFieldValue.class,
                MultiUserFieldValue.class, AttachmentList.class,
                CommentList.class,
                Issue.class,
                IssueAttachment.class,
                IssueComment.class,
                IssueCompactList.class,
                IssueLink.class,
                IssueProjectList.class,
                IssueTag.class,
                BaseItemList.class,
                LinkList.class,
                ProjectList.class,
                TagList.class,
                Error.class);
        final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        result = jaxbUnmarshaller.unmarshal(streamReader);
        if(result instanceof Error) {
            Error error = (Error) result;
            error.setCode(method.getStatusLine().getStatusCode());
            throw new CommandExecutionException(this, error);
        }
        return result;
    }
    public HttpMethodBase getMethod() {
        return method;
    }
    public abstract void createCommandMethod() throws Exception;
    public void addParameter(final @Nonnull String name, final @Nullable String value) {
        parameters.put(name, value);
    }
    public void removeParameter(final @Nonnull String name) {
        if(parameters.containsKey(name)) parameters.remove(name);
    }
    /**
     * Class to work around the JAXB name handling.
     * Forces upper case on the first letter of xsi:type attribute.
     */
    private class HackedReader extends StreamReaderDelegate {
        public HackedReader(XMLStreamReader xmlStreamReader) {
            super(xmlStreamReader);
        }
        @Override
        public String getAttributeValue(int index) {
            final String attributeValue = super.getAttributeValue(index);
            return ("type".equals(getAttributeLocalName(index))) ?
                    attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1)
                    : super.getAttributeValue(index);
        }
    }
}
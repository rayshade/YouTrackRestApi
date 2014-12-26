package youtrack.commands.base;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.httpclient.HttpMethodBase;
import youtrack.BaseItem;
import youtrack.Error;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public abstract class Command<O extends BaseItem, R> {
    protected final O owner;
    protected HttpMethodBase method;

    public Command(final @NotNull O owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Command " + this.getClass().getSimpleName() + "{" +
                "owner=" + owner +
                '}';
    }

    @NotNull
    public O getOwner() {
        return owner;
    }

    public boolean usesAuthorization() {
        return true;
    }

    @Nullable
    public abstract R getResult() throws CommandExecutionException;

    /**
     * Helper method to deserealize XML to objects. Used to interpret XML response received from YouTrack.
     *
     * @param xmlString Raw XML code.
     * @return Instance of an object.
     * @throws javax.xml.bind.JAXBException
     * @throws IOException
     */
    protected Object objectFromXml(final @NotNull String xmlString) throws JAXBException, IOException, XMLStreamException {
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        final XMLStreamReader streamReader = new HackedReader(xmlInputFactory.createXMLStreamReader(new StringReader(xmlString)));
        final JAXBContext jaxbContext = JAXBContext.newInstance("youtrack.issue.fields:youtrack.issue.fields.values:youtrack");
        final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        final Object result = jaxbUnmarshaller.unmarshal(streamReader);
        if (result instanceof Error) {
            Error error = (Error) result;
            throw new IOException("Error: " + error.getMessage());
        }
        return result;
    }

    public abstract HttpMethodBase commandMethod(final @NotNull String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException;

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
            return (getAttributeLocalName(index).equals("type")) ?
                    attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1)
                    : super.getAttributeValue(index);
        }
    }
}
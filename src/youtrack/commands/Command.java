package youtrack.commands;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.httpclient.HttpMethodBase;
import org.xml.sax.SAXException;
import youtrack.BaseItem;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
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
    HttpMethodBase method;

    public Command(final @NotNull O owner) {
        this.owner = owner;
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
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws javax.xml.bind.JAXBException
     * @throws org.xml.sax.SAXException
     * @throws IOException
     */
    Object objectFromXml(String xmlString) throws ParserConfigurationException, JAXBException, SAXException, IOException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xmlString));
        streamReader = new HackedReader(streamReader);
        JAXBContext jaxbContext = JAXBContext.newInstance("youtrack.issue.*");
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(streamReader);
    }

    public abstract HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException;

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
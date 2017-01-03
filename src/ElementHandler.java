import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class ElementHandler {

	public ElementHandler startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		return null;
	}

	public abstract boolean endElement(String uri, String localName, String qName) throws SAXException;

	public void characters(char ch[], int start, int length) throws SAXException {
	}

}

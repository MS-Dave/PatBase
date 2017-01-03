import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RootHandler extends ElementHandler {

	private final List<Patent> patents;

	public RootHandler(List<Patent> patents) {
		this.patents = patents;
	}

	@Override
	public ElementHandler startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

		if (qName.equalsIgnoreCase("FAMILY")) {
			return new FamilyHandler(patent -> patents.add(patent));
		}
		return null;
	}

	@Override
	public boolean endElement(String uri, String localName, String qName) throws SAXException {
		return false;
	}

}

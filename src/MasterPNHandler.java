import java.util.function.Consumer;

import org.xml.sax.SAXException;

public class MasterPNHandler extends ElementHandler {

	private final Consumer<String> masterPNListener;

	public MasterPNHandler(Consumer<String> masterPNListener) {
		this.masterPNListener = masterPNListener;
	}

	@Override
	public boolean endElement(String uri, String localName, String qName) throws SAXException {
		return qName.equalsIgnoreCase("MasterPN");
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		masterPNListener.accept(new String(ch, start, length));
	}

}

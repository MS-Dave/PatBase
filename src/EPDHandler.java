import java.util.Optional;
import java.util.function.Consumer;

import org.xml.sax.SAXException;

public class EPDHandler extends ElementHandler {

	private Consumer<Optional<Integer>> epdListener;

	public EPDHandler(Consumer<Optional<Integer>> epdListener) {
		this.epdListener = epdListener;
	}

	@Override
	public boolean endElement(String uri, String localName, String qName) throws SAXException {
		return qName.equalsIgnoreCase("EPD");

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String dateString = new String(ch, start, length);

		try {
			epdListener.accept(Optional.of(Integer.valueOf(dateString)));
		} catch (NumberFormatException e) {
			epdListener.accept(Optional.empty());
		}

	}

}

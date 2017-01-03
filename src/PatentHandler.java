import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

final class PatentHandler extends DefaultHandler {

	private Stack<ElementHandler> handlers = new Stack<ElementHandler>();
	private List<Patent> patents = new ArrayList<Patent>();

	public PatentHandler() {
		handlers.push(new RootHandler(patents));
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		ElementHandler nextHandler = handlers.peek().startElement(uri, localName, qName, attributes);
		if (nextHandler != null) {
			handlers.push(nextHandler);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		handlers.peek().characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (handlers.peek().endElement(uri, localName, qName)) {
			handlers.pop();
		}
	}

	public List<Patent> getPatents() {
		return patents;
	}

}
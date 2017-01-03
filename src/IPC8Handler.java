import java.util.function.Consumer;

import org.xml.sax.SAXException;

public class IPC8Handler extends ElementHandler {

	private Consumer<String> ipcHandler;

	public IPC8Handler(Consumer<String> ipcHandler) {
		this.ipcHandler = ipcHandler;
	}

	@Override
	public boolean endElement(String uri, String localName, String qName) throws SAXException {
		return qName.equalsIgnoreCase("IPC8");

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		ipcHandler.accept(new String(ch, start, length));
	}

}

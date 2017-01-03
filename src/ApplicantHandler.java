import java.util.function.Consumer;

import org.xml.sax.SAXException;

public class ApplicantHandler extends ElementHandler {

	private final Consumer<String> applicantListener;

	public ApplicantHandler(Consumer<String> applicantListener) {
		this.applicantListener = applicantListener;
	}

	@Override
	public boolean endElement(String uri, String localName, String qName) throws SAXException {
		return qName.equalsIgnoreCase("PATENTAPPLICANT");
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		applicantListener.accept(new String(ch, start, length));
	}

}

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FamilyHandler extends ElementHandler {

	private final List<String> ipcs = new ArrayList<>();
	private final List<String> applicants = new ArrayList<>();
	private final Consumer<Patent> patentListener;

	private Optional<Integer> epd;
	private String masterPN;

	FamilyHandler(Consumer<Patent> patentListener) {
		this.patentListener = patentListener;
	}

	@Override
	public ElementHandler startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

		switch (qName.toUpperCase()) {
		case "IPC8":
			return new IPC8Handler(ipc -> ipcs.add(ipc));
		case "MASTERPN":
			return new MasterPNHandler(masterPN -> this.masterPN = masterPN);
		case "EPD":
			return new EPDHandler(epd -> this.epd = epd);
		case "PATENTAPPLICANT":
			return new ApplicantHandler(applicant -> applicants.add(applicant));
		default:
		}
		return null;
	}

	@Override
	public boolean endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("FAMILY")) {
			Patent patent = new Patent(masterPN, epd, ipcs, applicants);
			patentListener.accept(patent);
			return true;
		}
		return false;
	}

}

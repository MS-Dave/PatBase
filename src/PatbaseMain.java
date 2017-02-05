
/*
Executable program


This program is written to read in .xml data files from the Patent Data Base and returns key elements
An SAX parser is chosen to process given .xml data faster as analysis is the priority and no changes within the .xml file as can be done
with a DOM XML parser.

*/

import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class PatbaseMain {

	public static void main(String[] args) {

		List<Patent> patents = getPatents();

		// Gets the list of all patent applicants in the called xml
		HashMap<String, Integer> allApplicants = getAllApplicants(patents);
		// HashMap<String, Integer> allDates = getDates(patents);

		// Assigning the company to a certain patent
		// Iterator<String> allApplicantIterator =
		// allApplicants.keySet().iterator();

		determineCompanies(patents, allApplicants);

		for (Patent patent : patents) {
			System.out.println("The MasterPN is: " + patent.getMasterPN() + " for which the company "
					+ patent.getCompany() + " applied for.");
		}

		/////////////////////////////////////////////////////////
		// Aktivitätskennzahlen
		////////////////////////////////////////////////////////

		// 1. Patentanmeldungen: Anzahl an Patenten pro Analyseobjekt int
		// Alle Patentanmeldungen nach 2005
		//

		for (Patent patent : patents) {

			System.out.println(patent.getEPD());

		}
	}

	private static void determineCompanies(List<Patent> patents, HashMap<String, Integer> allApplicants) {
		for (Patent patent : patents) {
			String company = "";
			int largestValue = 0;

			for (String applicant : patent.getApplicants()) {
				if (allApplicants.containsKey(applicant)) {
					int occ = allApplicants.get(applicant);
					if (occ > largestValue) {
						company = applicant;
						largestValue = occ;
					}
				}
			}
			patent.setCompany(company);
		}
	}

	private static HashMap<String, Integer> getAllApplicants(List<Patent> patents) {
		HashMap<String, Integer> allApplicants = new HashMap<String, Integer>();

		for (Patent patent : patents) {
			for (String applicant : patent.getApplicants()) {
				if (allApplicants.containsKey(applicant)) {
					int counter = allApplicants.get(applicant);
					counter++;
					allApplicants.put(applicant, counter);
				} else {
					allApplicants.put(applicant, 1);
				}
			}
		}
		return allApplicants;
	}

	/*
	 * private static HashMap<String, Integer> getDates(List<Patent> patents) {
	 * HashMap<String, Integer> allDates = new HashMap<String, Integer>();
	 * 
	 * for (Patent patent : patents) { String MasterPN = patent.getMasterPN();
	 * int epd = patent.getEPD(); allDates.put(MasterPN, epd); } return
	 * allDates; }
	 */
	private static List<Patent> getPatents() {
		PatentHandler handler = new PatentHandler();

		// Tries are there to throw exceptions. Exceptions are errors which
		// would return an error, but are bundled in a catch statement
		// and returned when needed. The catch statement can be seen blow
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			saxParser.parse("c:\\Users\\David\\Project_Patent\\PatBase-Daten\\" + "PatBaseTest.xml", handler);

			// saxParser.parse("c:\\Users\\David\\Project_Patent\\PatBase-Daten\\"
			// + "Test_XML_IPC.xml", handler);

			// saxParser.parse("c:\\Users\\David\\Project_Patent\\PatBase-Daten\\"
			// + "XML_Techfield.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler.getPatents();
	}

}

/*
 * 
 * 
 * // Iterator to create the IPC8 Mapping for Occurences Iterator<String>
 * keySetIterator = IPC8Map.keySet().iterator(); int PatentCounter = 0;
 * while(keySetIterator.hasNext()){ String key = keySetIterator.next();
 * System.out.println(key + "     Occurences: " + IPC8Map.get(key));
 * PatentCounter = PatentCounter +IPC8Map.get(key); }
 */
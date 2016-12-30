/*
Executable program

This program is written to read in .xml data files from the Patent Data Base and returns key elements
An SAX parser is chosen to process given .xml data faster as analysis is the priority and no changes within the .xml file as can be done
with a DOM XML parser.

*/



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;




public class PatbaseMain {

	public static void main(String[] args) {

		ArrayList<Patent> PatentList = new ArrayList<Patent>();
		ArrayList<Company> CompanyList = new ArrayList<Company>();
		
		//Tries are there to throw exceptions. Exceptions are errors which would return an error, but are bundled in a catch statement
		//and returned when needed. The catch statement can be seen blow
		try{
			
		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			
			//Creates the object handler from the class DefaultHandler 
			DefaultHandler handler = new DefaultHandler(){
				
				//Defines the Family as outer bound for a patent, EPD as earliest publication date, MasterPN as unique identification number for a patent,
				//IPC8 as the technology fields the product is in, Applicant as the Applicants (Can be either a single person or company) for a specific patent
				boolean bfFamily 			 = false;
				boolean bfEPD				 = false;
				boolean bfMasterPN			 = false;
				boolean bfIPC8    			 = false;
				boolean bfApplicant			 = false;
				
				
				//Value container used for family specific actions
				int EPD = 0;
				String MasterPN;
				ArrayList<String> IPC8Safe = new ArrayList<String>();
				ArrayList<String> ApplicantList= new ArrayList<String>();
				
				
				
				//Changes the startElement method of the Content Handler
				//It activates the different tags from the .xml file	
				public void startElement(String uri, String localName, String qName,
							Attributes attributes) throws SAXException {
	
					if (qName.equalsIgnoreCase("FAMILY")){
						bfFamily = true;
						IPC8Safe.clear();
						ApplicantList.clear();
						}
					if (qName.equalsIgnoreCase("PATENTAPPLICANT")){
						bfApplicant = true;
					}
					if (qName.equalsIgnoreCase("IPC8")){
						bfIPC8 = true;
					}
					if (qName.equalsIgnoreCase("MASTERPN")){
						bfMasterPN = true;
					}
					if (qName.equalsIgnoreCase("EPD")){
						bfEPD= true;
					}
				}
				
				//Methods to populate patent objects
				public void putDate(char ch[], int start, int length){
					String date = new String(ch,start,length);
					   try {
					        EPD=Integer.parseInt(date);
					   }catch (NumberFormatException e){
					   //an empty catch
					   } 
				}
	
				public void putMasterPN(char ch[], int start, int length){
					String pn = new String(ch,start,length);
					MasterPN = pn;
				}
				
				public String IPC8List(char ch[], int start, int length){
					String IPC = new String(ch,start,length);
					return IPC;
				}
				
				public String PatentApplicant(char ch[], int start, int length){
					String Applicants = new String(ch,start,length);
					return Applicants;
				}
				
				//Method use to populate family specific array lists				
				public ArrayList<String> populateArray(ArrayList<String> list){
					
					ArrayList<String> populatedArray = new ArrayList<String>();
					for(int i = 0; i < list.size();i++){
						String entry = list.get(i);
						populatedArray.add(entry);
					}
					return populatedArray;
				}
				
				
								
				//Default Method of the Handler extended for certain elements
				public void characters(char ch[], int start, int length)
						throws SAXException{
						
					if(bfIPC8){
						String IPC = IPC8List(ch,start,length);
						IPC8Safe.add(IPC);
						bfIPC8 = false;
					}
					if(bfApplicant){
						String Applicants = PatentApplicant(ch,start,length);
						ApplicantList.add(Applicants);	
						bfApplicant = false;
					}
					if(bfMasterPN){
						putMasterPN(ch, start,length);
						bfMasterPN = false;
					}
					if(bfEPD){
						putDate(ch,start,length);
						bfEPD = false;
					}
				
				}
				
				//Populates the Patent Objects with the values taken out of the patent family
				public void endElement(String uri, String localName, String qName)
						throws SAXException{
				
					if(qName.equalsIgnoreCase("FAMILY")){
						
						ArrayList<String> IPC8 = populateArray(IPC8Safe);
						ArrayList<String> PatentApplicants = populateArray(ApplicantList);
						Patent patent = new Patent(MasterPN, EPD, IPC8, PatentApplicants);
						PatentList.add(patent);
						bfFamily = false;
					}
				
				}
			};
			
				
				
				
					//saxParser.parse("c:\\Users\\David\\Project_Patent\\PatBase-Daten\\"
					//	+ "PatBaseTest.xml", handler);
				
				  	saxParser.parse("c:\\Users\\David\\Project_Patent\\PatBase-Daten\\"
						+ "Test_XML_IPC.xml", handler);
				  	
				  	//saxParser.parse("c:\\Users\\David\\Project_Patent\\PatBase-Daten\\"
					//		+ "XML_Techfield.xml", handler);
			
			} catch(Exception e){
				e.printStackTrace();
			}
		
		
		
		
		
		//Gets the list of all patent applicants in the called xml
		HashMap<String,Integer> allApplicants = new HashMap<String,Integer>();
		
		for(int i = 0; i < PatentList.size();i++){
			ArrayList<String> Applicants = new ArrayList<String>();
			Applicants = PatentList.get(i).getApplicant();
			
			for(int j = 0; j < Applicants.size();j++){
				String Applicant = Applicants.get(j);
				
				if(allApplicants.containsKey(Applicant)){
					int counter = allApplicants.get(Applicant);
					counter++;
					allApplicants.put(Applicant, counter);
				} else {
					allApplicants.put(Applicant, 1);
				}
			}
		}
		
		
		
		
		//Assigning the company to a certain patent
		//Iterator<String> allApplicantIterator = allApplicants.keySet().iterator();
		
		
		for(int i = 0; i < PatentList.size();i++){
			ArrayList<String> patentAppl = PatentList.get(i).getApplicant();
			String company = "";
			int largestValue = 0;
			
			
			for(int j=0; j < patentAppl.size();j++){
				String name = patentAppl.get(j);
				if(allApplicants.containsKey(name)){
					int occ = allApplicants.get(name);
					if(occ> largestValue){
						company=name;
						System.out.println(company);
					}
				}
				//System.out.println(name + ".....");
				
			}
			//System.out.println(company);
			patentAppl.clear();
			PatentList.get(i).setCompany(company);	
		}
		
		
	for(int i = 0; i < PatentList.size();i++){
		System.out.println("The MasterPN is: " + PatentList.get(i).getMasterPN() + " for which the company " + PatentList.get(i).getCompany() + " applied for.");
	}
		
		
		
		
		
		
		
		
		
		
		
		
		

	/*	
		for(int i = 0; i < PatentList.size();i++){
			System.out.println(PatentList.get(i).getMasterPN());
			System.out.println(PatentList.get(i).getEPD());
			System.out.println(PatentList.get(i).getIPC8());
			System.out.println(PatentList.get(i).getApplicant());
		}
	*/	
		

	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/////////////////////////////////////////////////////////
		//Aktivitätskennzahlen
		////////////////////////////////////////////////////////
		
		
		
		
		
		
		
		
/*		
		//1. Patentanmeldungen: Anzahl an Patenten pro Analyseobjekt
		int PatentAnmeldungen = PatentList.size();
		System.out.println("Anzahl an Patentanmeldungen: " + PatentAnmeldungen);
		
		
		
		
			//1.1 Jährliche Patentanmeldungen
			
			for(int i=0; i < PatentList.size(); i++){
				
			}
		
		
		
		
		//2. Patentanteil: 
		
		//3. Patentanmeldungsaktivität (Branchenschnitt): 

		//4. Patentbasierter Unternehmensbenchmark: 
		
		//5. Patentintensität: 
		
		//6. Prüfquote: 
		
		//7. Patentwachstumsrate: 
		
		//8. Internes Aktivitätsprofil: 
		
		//9. Interne Konzentrationsquote: 
		
		//10. Technologieanteil: 
		
		//11. Externe Konzentrationsquote
		
		//12. Aktivitätsindex
		
		//13. Patentbasierter Technologiebenchmark
		
		//14. Anmelderaktivität
		
		//15. Patentbasierter Erfinderbenchmark
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Qualitätskennzahlen
		
		
			
		
*/		
		
		
		
		
		
	}	




}		
		
		
		
		
		
		
		
/*		
		
		
		// Iterator to create the IPC8 Mapping for Occurences
		Iterator<String> keySetIterator = IPC8Map.keySet().iterator();
		int PatentCounter = 0;
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
	    	System.out.println(key + "     Occurences: " + IPC8Map.get(key));
	    	PatentCounter = PatentCounter +IPC8Map.get(key);
	    }
*/
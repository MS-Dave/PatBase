import java.util.ArrayList;




public class Patent {

	// IPC8 are technologies field in which the patent is assigned/registered	
	ArrayList<String> IPC8 = new ArrayList<String>();
	
	// Earliest Publication Date (EPD) - Gives the date of the first publication
	int EPD;
	
	// MasterPN returns a unique patent number based in which area the application was filed
	String MasterPN;
	
	// Applicants who are assigned to the Patent
	ArrayList<String> Applicants = new ArrayList<String>();
	
	//Company related to the assigned patent
	String Company;
	
	public Patent(String MasterPN, int date, ArrayList<String> IPC, ArrayList<String> Applicant){
		
		this.IPC8 = IPC;
		this.EPD = date;
		this.MasterPN = MasterPN;
		this.Applicants = Applicant;
		this.Company = "";
	}
	
	public String getMasterPN(){
		return MasterPN;
	}
	
	public int getEPD(){
		return EPD;
	}
	
	public ArrayList<String> getIPC8(){
		return IPC8;
	}
	
	public ArrayList<String> getApplicant(){
		return Applicants;
	}
	
	public String getCompany(){
		return Company;
	}
	
	public void setCompany(String company){
		this.Company = company;
	}
	
	
	
}

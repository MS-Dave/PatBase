import java.util.ArrayList;

public class Company {

	ArrayList<Patent> PatentL = new ArrayList<Patent>();
	String CompanyName;
	
	
	public Company(String company, ArrayList<Patent> PatentList){
		this.PatentL = PatentList;
		this.CompanyName = company;
	}
	
	public ArrayList<Patent> getPatentList(){
		return PatentL;
	}
	
	public String getCompany(){
		return CompanyName;
	}
	
}

import java.util.List;
import java.util.Optional;

public class Patent {

	// IPC8 are technologies field in which the patent is assigned/registered
	private final List<String> IPC8;

	// Earliest Publication Date (EPD) - Gives the date of the first publication
	private final Optional<Integer> EPD;

	// MasterPN returns a unique patent number based in which area the
	// application was filed
	private final String MasterPN;

	// Applicants who are assigned to the Patent
	private final List<String> Applicants;

	// Company related to the assigned patent
	String Company;

	public Patent(String MasterPN, Optional<Integer> epd, List<String> ipcs, List<String> applicants) {
		this.IPC8 = ipcs;
		this.EPD = epd;
		this.MasterPN = MasterPN;
		this.Applicants = applicants;
		this.Company = "";
	}

	public String getMasterPN() {
		return MasterPN;
	}

	public int getEPD() {
		return EPD.orElse(Integer.valueOf(0)).intValue();
	}

	public List<String> getIPC8() {
		return IPC8;
	}

	public List<String> getApplicants() {
		return Applicants;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		this.Company = company;
	}

}

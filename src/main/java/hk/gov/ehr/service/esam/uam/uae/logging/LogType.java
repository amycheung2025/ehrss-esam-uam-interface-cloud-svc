package hk.gov.ehr.service.esam.uam.uae.logging;

public enum LogType {
	AUDIT("A"), DEBUG("D"), SECURITY("S");

	private final String logTypeCd;

	private LogType(String logTypeCd) {
		this.logTypeCd = logTypeCd;
	}

	public String getLogTypeCd() {
		return logTypeCd;
	}

}

package hk.gov.ehr.service.esam.uam.uae.logging;

public enum SeverityCd {
	INFO("I"), WARNING("W"), CRITICAL("C");

	private final String severityCode;

	private SeverityCd(String severityCode) {
		this.severityCode = severityCode;
	}

	public String getSeverityCode() {
		return severityCode;
	}
}

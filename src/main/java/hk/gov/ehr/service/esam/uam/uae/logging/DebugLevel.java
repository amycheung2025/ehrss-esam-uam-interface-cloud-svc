package hk.gov.ehr.service.esam.uam.uae.logging;

public enum DebugLevel {
	LIFECYCLE("10"), FLOW("20"), STEP("30"), DATA("40");

	private final String level;

	private DebugLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}
}

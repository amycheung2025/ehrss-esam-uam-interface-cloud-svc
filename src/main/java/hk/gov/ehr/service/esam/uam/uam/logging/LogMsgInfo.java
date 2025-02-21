package hk.gov.ehr.service.esam.uam.uam.logging;

public class LogMsgInfo {
	private String content;
	private Throwable stackTrace;
	private String[] searchVarchar;
	private String text;
	private long startTime;

	public LogMsgInfo(String content, Throwable stackTrace, String[] searchVarchar, String text, long startTime) {
		super();
		this.content = content;
		this.stackTrace = stackTrace;
		this.searchVarchar = searchVarchar;
		this.text = text;
		this.startTime = startTime;
	}

	public LogMsgInfo() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Throwable getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(Throwable stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String[] getSearchVarchar() {
		return searchVarchar;
	}

	public void setSearchVarchar(String[] searchVarchar) {
		this.searchVarchar = searchVarchar;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}

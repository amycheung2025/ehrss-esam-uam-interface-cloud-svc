package hk.gov.ehr.service.esam.uam.uae.logging;

import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import hk.gov.ehr.service.esam.uam.uae.constants.Constant;
import hk.gov.ehr.service.tch.als.AlsLogger;
import hk.gov.ehr.service.tch.als.AlsMessage;
import hk.gov.ehr.service.tch.als.lib.exception.AlsException;

@Service
public class SvcLogger {
	private static final String ENV_FACNIG = "EHC";
	public static final String LOG_START = "START";
	public static final String LOG_END = "END";
	public static final String LOG_INFO = "INFO";
	public static final String LOG_EXCEPTION = "EXCEPTION";

	public static final String TOOL_CD = "AS";

	private AlsLogger logger;
	private String serviceCd;
	private String serviceVer;

	public SvcLogger() {
		super();
		this.logger = new AlsLogger();
		this.serviceCd = Constant.SYS_SERVICE_CODE;
		this.serviceVer = String.valueOf(Constant.SYS_MAJOR_VERSION);
	}
	
	public SvcLogger(AlsLogger logger) {
		super();
		this.logger = logger;
		this.serviceCd = Constant.SYS_SERVICE_CODE;
		this.serviceVer = String.valueOf(Constant.SYS_MAJOR_VERSION);
	}

	public Long genCorrelationId() {
		return logger.generateCorrelationId(ENV_FACNIG, "SERVICE");
	}

	public void aStart(String passAlongInfo, String consumerServiceCd, String content, String[] searchVarchar) {
		logAudit(SeverityCd.INFO, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_START, new LogMsgInfo(content, null, searchVarchar, null, -9l));
	}

	public void aEnd(String passAlongInfo, String consumerServiceCd, String content, String[] searchVarchar, long startTime) {
		logAudit(SeverityCd.INFO, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_END, new LogMsgInfo(content, null, searchVarchar, null, startTime));
	}
	
	public void aEnd(String passAlongInfo, String consumerServiceCd, String content,String text, String[] searchVarchar, long startTime) {
		logAudit(SeverityCd.INFO, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_END, new LogMsgInfo(content, null, searchVarchar, text, startTime));
	}

	public void aX(String passAlongInfo, String consumerServiceCd, String content, Throwable stackTrace,
			String[] searchVarchar) {
		logAudit(SeverityCd.INFO, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_EXCEPTION, new LogMsgInfo(content, stackTrace, searchVarchar, null, -9l));
	}

	public void aWarn(String passAlongInfo, String consumerServiceCd, String content, Throwable stackTrace,
			String[] searchVarchar, String text) {
		logAudit(SeverityCd.WARNING, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_EXCEPTION, new LogMsgInfo(content, stackTrace, searchVarchar, text, -9l));
	}

	public void aCritical(String passAlongInfo, String consumerServiceCd, String content, Throwable stackTrace,
			String[] searchVarchar, String text) {
		logAudit(SeverityCd.CRITICAL, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_EXCEPTION, new LogMsgInfo(content, stackTrace, searchVarchar, text, -9l));
	}

	public void d(String passAlongInfo, DebugLevel debugLevel, String consumerCd, String content) {
		logDebug(debugLevel, consumerCd, passAlongInfo, Thread.currentThread().getStackTrace()[2], LOG_INFO,
				new LogMsgInfo(content, null, null, null, -9l));
	}

	public void dStart(String passAlongInfo, DebugLevel debugLevel, String consumerCd, String content) {
		logDebug(debugLevel, consumerCd, passAlongInfo, Thread.currentThread().getStackTrace()[2], LOG_START,
				new LogMsgInfo(content, null, null, null, -9l));
	}

	public void dEnd(String passAlongInfo, DebugLevel debugLevel, String consumerCd, String content, long startTime) {
		logDebug(debugLevel, consumerCd, passAlongInfo, Thread.currentThread().getStackTrace()[2], LOG_END,
				new LogMsgInfo(content, null, null, null, startTime));
	}

	public void dSAction(String passAlongInfo, DebugLevel debugLevel, String consumerCd, String action, String content,
			String[] searchVarchar) {
		logDebug(debugLevel, consumerCd, passAlongInfo, Thread.currentThread().getStackTrace()[2], action,
				new LogMsgInfo(content, null, searchVarchar, null, -9l));
	}

	public void sWarn(String passAlongInfo, String consumerServiceCd, String content, Throwable stackTrace,
			String[] searchVarchar) {
		logSecurity(SeverityCd.WARNING, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_EXCEPTION, new LogMsgInfo(content, stackTrace, searchVarchar, null, -9l));
	}

	public void sCritical(String passAlongInfo, String consumerServiceCd, String content, Throwable stackTrace,
			String[] searchVarchar) {
		logSecurity(SeverityCd.CRITICAL, consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
				SvcLogger.LOG_EXCEPTION, new LogMsgInfo(content, stackTrace, searchVarchar, null, -9l));
	}
	
    public void writeDebugLogWithScramble(String passAlongInfo, String consumerServiceCd, String content,String scrambleText,String action,
			String[] searchVarchar) {
    	logDebugScrambleText(DebugLevel.LIFECYCLE,consumerServiceCd, passAlongInfo, Thread.currentThread().getStackTrace()[2],
    			action,scrambleText, new LogMsgInfo(content, null, searchVarchar, null, -9l));
    }

	public String genPassAlongInfo(Long hcpId, Long ehrUid, String userCd, Long correlationId, String txnCd) {
		return AlsLogger.generatePassAlongInfo(hcpId, ehrUid, userCd, correlationId, txnCd);
	}
	
	public long getCurrentTime() {
		return new AlsLogger().getCurrentTime();
	}

	private void logAudit(SeverityCd audit, String consumerServiceCd, String passAlongInfo, StackTraceElement method,
			String actionCd, LogMsgInfo logMsgInfo) {
		if (logger != null) {
			String className = Thread.currentThread().getStackTrace()[3].getClassName();
			className = className.substring(className.lastIndexOf(".") + 1);
			AlsMessage alsMsg = getLogEntriesMap(consumerServiceCd, className,
					method.getMethodName() + ":" + method.getLineNumber(), actionCd, logMsgInfo);
			alsMsg.setLogTypeCd(LogType.AUDIT.getLogTypeCd());
			alsMsg.setSeverityCd(audit.getSeverityCode());

			try {
				logger.log(passAlongInfo, alsMsg);
			} catch (Exception e) {
				throw new AlsException(e);
			}
		}
	}

	private void logDebug(DebugLevel debugLevel, String consumerServiceCd, String passAlongInfo,
			StackTraceElement method, String actionCd, LogMsgInfo logMsgInfo) {
		if (logger != null) {
			String className = Thread.currentThread().getStackTrace()[3].getClassName();
			className = className.substring(className.lastIndexOf(".") + 1);

			AlsMessage alsMsg = getLogEntriesMap(consumerServiceCd, className,
					method.getMethodName() + ":" + method.getLineNumber(), actionCd, logMsgInfo);
			alsMsg.setLogTypeCd(LogType.DEBUG.getLogTypeCd());
			alsMsg.setSeverityCd(debugLevel.getLevel());

			try {
				logger.log(passAlongInfo, alsMsg);
			} catch (Exception e) {
				throw new AlsException(e);
			}
		}
	}

	private void logSecurity(SeverityCd security, String consumerServiceCd, String passAlongInfo,
			StackTraceElement method, String actionCd, LogMsgInfo logMsgInfo) {
		if (logger != null) {
			String className = Thread.currentThread().getStackTrace()[3].getClassName();
			className = className.substring(className.lastIndexOf(".") + 1);
			AlsMessage alsMsg = getLogEntriesMap(consumerServiceCd, className,
					method.getMethodName() + ":" + method.getLineNumber(), actionCd, logMsgInfo);
			alsMsg.setLogTypeCd(LogType.SECURITY.getLogTypeCd());
			alsMsg.setSeverityCd(security.getSeverityCode());
			
			try {
				logger.log(passAlongInfo, alsMsg);
			} catch (Exception e) {
				throw new AlsException(e);
			}
		}
	}
	
	private void logDebugScrambleText(DebugLevel debugLevel,String consumerServiceCd, String passAlongInfo,
			StackTraceElement method, String actionCd,String scrambleText, LogMsgInfo logMsgInfo) {
		if (logger != null) {
			String className = Thread.currentThread().getStackTrace()[3].getClassName();
			className = className.substring(className.lastIndexOf(".") + 1);
			AlsMessage alsMsg = getLogEntriesMap(consumerServiceCd, className,
					method.getMethodName() + ":" + method.getLineNumber(), actionCd, logMsgInfo);
			alsMsg.setLogTypeCd(LogType.DEBUG.getLogTypeCd());
			alsMsg.setSeverityCd(debugLevel.getLevel());
			alsMsg.setToolCd(TOOL_CD);
			alsMsg.setScrambledText(scrambleText);
			
			try {
				logger.log(passAlongInfo, alsMsg);
			} catch (Exception e) {
				throw new AlsException(e);
			}
		}
	}


	private AlsMessage getLogEntriesMap(String consumerServiceCd, String operationCd, String subOperation,
			String actionCd, LogMsgInfo logMsgInfo) {
		var alsMsg = new AlsMessage();
		alsMsg.setServiceCd(serviceCd);
		alsMsg.setServiceVer(serviceVer);
		alsMsg.setConsumerServiceCd(consumerServiceCd);
		alsMsg.setToolCd(TOOL_CD);

		if (operationCd != null) {
			alsMsg.setOperationCd(operationCd);
		}
		if (subOperation != null) {
			alsMsg.setSubOperation(subOperation);
		}

		alsMsg.setActionCd(actionCd);

		if (logMsgInfo.getContent() != null) {
			alsMsg.setContent(logMsgInfo.getContent());
		}
		if (logMsgInfo.getStackTrace() != null) {
			alsMsg.setStackTrace(ExceptionUtils.getStackTrace(logMsgInfo.getStackTrace()));
		}

		if (logMsgInfo.getText() != null) {
			alsMsg.setText(logMsgInfo.getText());
		}
		
		if(logMsgInfo.getStartTime() != -9) {
			alsMsg.setDuration(calculateDuration(logMsgInfo.getStartTime()));
		}

		String[] searchVarChar = logMsgInfo.getSearchVarchar();
		if (searchVarChar != null) {
			if (searchVarChar.length > 10) {
				searchVarChar = Arrays.copyOf(searchVarChar, 10);
			}

			setSearchVarChar(searchVarChar, alsMsg);
		}

		return alsMsg;
	}
	
	private long calculateDuration(long startTime) {
		if (logger == null) {
			logger = new AlsLogger();
		}
		return logger.getDuration(startTime);
	}

	private void setSearchVarChar(String[] searchVarChar, AlsMessage alsMsg) {
		int searchVarCharLen = searchVarChar.length;
		if (searchVarCharLen >= 1) {
			alsMsg.setSearchVarchar1(searchVarChar[0]);
		}

		if (searchVarCharLen >= 2) {
			alsMsg.setSearchVarchar2(searchVarChar[1]);
		}

		if (searchVarCharLen >= 3) {
			alsMsg.setSearchVarchar3(searchVarChar[2]);
		}

		if (searchVarCharLen >= 4) {
			alsMsg.setSearchVarchar4(searchVarChar[3]);
		}

		if (searchVarCharLen >= 5) {
			alsMsg.setSearchVarchar5(searchVarChar[4]);
		}

		if (searchVarCharLen >= 6) {
			alsMsg.setSearchVarchar6(searchVarChar[5]);
		}

		if (searchVarCharLen >= 7) {
			alsMsg.setSearchVarchar7(searchVarChar[6]);
		}

		if (searchVarCharLen >= 8) {
			alsMsg.setSearchVarchar8(searchVarChar[7]);
		}

		if (searchVarCharLen >= 9) {
			alsMsg.setSearchVarchar9(searchVarChar[8]);
		}

		if (searchVarCharLen >= 10) {
			alsMsg.setSearchVarchar10(searchVarChar[9]);
		}

	}

	public void setLogger(AlsLogger logger) {
		this.logger = logger;
	}

}

package hk.gov.ehr.service.esam.uam.uam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hk.gov.ehr.service.esam.uam.uam.common.Result;
import hk.gov.ehr.service.esam.uam.uam.logging.SvcLogger;

@RestController
@RequestMapping(value = "/initDataService")
public class InitDataController {
	
	@Autowired
	private SvcLogger logger;

	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.setDisallowedFields(new String[]{});
	}
	
	@RequestMapping(value = "/isEhr_obEnabled", method = RequestMethod.GET)
	public Result isEhr_obEnabled(HttpServletRequest request){

		String passAlongInfo = request.getHeader("EHR_PASSALONG");
		String consumerCd = request.getHeader("EHR_SER_CD");

		try{

			String result = "isEhr_obEnabled"+ "Y";
			
			return Result.success("Success", result);
			
		}catch(Exception e){
			logger.aCritical(passAlongInfo, consumerCd, "IsEhr_obEnabled request fail : " + e.getMessage(),e, null ,null);
			return Result.error("Service error: " + e.toString());
			
		}
	}
}

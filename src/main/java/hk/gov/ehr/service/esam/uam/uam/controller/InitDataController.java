package hk.gov.ehr.service.esam.uam.uam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hk.gov.ehr.service.esam.uam.uam.common.Result;
import hk.gov.ehr.service.esam.uam.uam.logging.SvcLogger;
import com.google.gson.Gson;


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
			
			Gson gson = new Gson();

			Map<String, String> data = new HashMap<>();
            data.put("isEhr_obEnabled", "Y");
			
			String json = gson.toJson(data);

			return Result.success(Result.SUCCES_CODE.toString(0), json);
			
		}catch(Exception e){
			logger.aCritical(passAlongInfo, consumerCd, "IsEhr_obEnabled request fail : " + e.getMessage(),e, null ,null);
			return Result.error("Service error: " + e.toString());
			
		}
	}
}

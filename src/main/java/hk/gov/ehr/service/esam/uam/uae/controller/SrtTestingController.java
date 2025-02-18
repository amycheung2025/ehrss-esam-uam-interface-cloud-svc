package hk.gov.ehr.service.esam.uam.uae.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hk.gov.ehr.service.esam.uam.uae.common.Result;

@RestController
@RequestMapping(value = "/")
public class SrtTestingController {

	@RequestMapping(value = "/srt-endpoint", method = RequestMethod.GET)
	public Result SrtEndpoint(){
		return Result.success("OK");
	}
}

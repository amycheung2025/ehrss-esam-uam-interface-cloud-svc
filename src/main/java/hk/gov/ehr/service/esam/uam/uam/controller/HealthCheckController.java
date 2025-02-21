package hk.gov.ehr.service.esam.uam.uam.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "health";
    }
}

package hk.gov.ehr.service.esam.uam.uae.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckController {

    @GetMapping("/probeHealthCheck")
    public String healthCheck() {
        return "health";
    }
}

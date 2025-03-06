package gov.nist.hit.hl7.bootstrap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint. No authentication required.";
    }

    @GetMapping("/authenticated")
    public String authenticatedEndpoint() {
        return "This is a protected endpoint. Authentication required.";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin-only endpoint. Role-based authorization required.";
    }
}

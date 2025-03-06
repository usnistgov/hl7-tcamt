package gov.nist.hit.hl7.bootstrap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
class ForwardingController {
//    @RequestMapping(value = { "/{path:(?!static$)[^.]*}/**", "/"})
//    public String redirect() {
//        return "forward:/static/index.html";
//    }


    @RequestMapping(value = {"/{path:(?!resources$)[^.]*}/**", "/"})
    public String redirect() {
        return "forward:/resources/index.html";
    }
}
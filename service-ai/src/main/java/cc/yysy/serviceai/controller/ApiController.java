package cc.yysy.serviceai.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Component
public class ApiController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}

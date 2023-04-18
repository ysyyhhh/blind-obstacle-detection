package cc.yysy.serviceobstacle.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Component
public class ApiController {
    static Logger logger = Logger.getLogger("ApiController log");

}

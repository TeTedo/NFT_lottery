package com.undefined.undefined.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping("/docs")
    public String viewDocs() {
        return "/docs/index.html";
    }

}

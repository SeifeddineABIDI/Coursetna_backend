package com.picloud.picloud.controller.googledrive;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Home", description = "Home")
public class HomeController {

    @GetMapping
    public String home() {
        return "test google drive api";
    }
}

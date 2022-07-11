package org.behrang.telecom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/phone-numbers")
public class PhoneNumberController {

    @GetMapping({"", "/"})
    public List<String> list() {
        return Collections.emptyList();
    }

}

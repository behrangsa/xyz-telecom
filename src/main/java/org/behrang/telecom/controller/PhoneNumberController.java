package org.behrang.telecom.controller;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.repository.PhoneNumberRepository;
import org.behrang.telecom.response.PhoneNumbersResponse;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/phone-numbers")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberRepository repository;

    @GetMapping({"", "/"})
    public HttpEntity<PhoneNumbersResponse> list() {
        final var phoneNumbers = repository.findAll(0, 69);
        final var payload = new PhoneNumbersResponse(phoneNumbers);
        payload.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).list()).withSelfRel());
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

}

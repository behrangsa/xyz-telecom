package org.behrang.telecom.controller;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.model.CollectionPayload;
import org.behrang.telecom.response.PhoneActivationResponse;
import org.behrang.telecom.response.PhoneNumbersResponse;
import org.behrang.telecom.service.PhoneNumberService;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(
        path = "/customers",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CustomerController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping(
            path = "/{customerId}/phone-numbers"
    )
    public HttpEntity<PhoneNumbersResponse> listPhoneNumbers(@PathVariable("customerId") UUID customerId) {
        return listPhoneNumbers(customerId, 0);
    }

    @GetMapping(
            path = "/{customerId}/phone-numbers",
            params = "pageNumber"
    )
    public HttpEntity<PhoneNumbersResponse> listPhoneNumbers(@PathVariable("customerId") UUID customerId, @RequestParam("pageNumber") final int pageNumber) {
        final var responsePayload = phoneNumberService.findAllByCustomerId(customerId, pageNumber);

        final var response = new PhoneNumbersResponse(responsePayload.content());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

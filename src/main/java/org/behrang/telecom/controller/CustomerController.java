package org.behrang.telecom.controller;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.response.CustomerPhoneNumbersResponse;
import org.behrang.telecom.service.PhoneNumberService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public HttpEntity<CustomerPhoneNumbersResponse> listPhoneNumbers(@PathVariable("customerId") UUID customerId) {
        return listPhoneNumbers(customerId, 0);
    }

    @GetMapping(
            path = "/{customerId}/phone-numbers",
            params = "pageNumber"
    )
    public HttpEntity<CustomerPhoneNumbersResponse> listPhoneNumbers(@PathVariable("customerId") UUID customerId, @RequestParam("pageNumber") final int pageNumber) {
        final var responsePayload = phoneNumberService.findAllByCustomerId(customerId, pageNumber);

        final var response = new CustomerPhoneNumbersResponse(responsePayload.content());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

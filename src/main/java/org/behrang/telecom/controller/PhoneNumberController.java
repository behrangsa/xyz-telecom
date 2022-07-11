package org.behrang.telecom.controller;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.response.PhoneActivationResponse;
import org.behrang.telecom.response.PhoneNumbersResponse;
import org.behrang.telecom.service.PhoneNumberService;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/phone-numbers")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping(path = {"", "/"})
    public HttpEntity<PhoneNumbersResponse> list() {
        return list(0);
    }

    @GetMapping(params = {"pageNumber"})
    public HttpEntity<PhoneNumbersResponse> list(@RequestParam("pageNumber") final int pageNumber) {
        final var responsePayload = phoneNumberService.findAll(pageNumber);

        final var response = new PhoneNumbersResponse(responsePayload.content());

        addSelfLink(response, pageNumber);

        if (responsePayload.hasPrev()) {
            addPrevLink(response, pageNumber);
        }

        if (responsePayload.hasNext()) {
            addNextLink(response, pageNumber);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/{phoneNumber}", params = {"action=activate"})
    public HttpEntity<PhoneActivationResponse> activate(@PathVariable("phoneNumber") final String phoneNumber) {
        return null;
    }

    private void addSelfLink(final PhoneNumbersResponse response, final int currentPage) {
        if (currentPage == 0) {
            response.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).list())
                            .withSelfRel()
            );
        } else {
            response.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).list(currentPage))
                            .withSelfRel()
            );
        }
    }

    private void addPrevLink(final PhoneNumbersResponse response, final int currentPage) {
        response.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).list(currentPage - 1))
                        .withRel(IanaLinkRelations.PREV)
        );
    }

    private void addNextLink(final PhoneNumbersResponse response, final int currentPage) {
        response.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).list(currentPage + 1))
                        .withRel(IanaLinkRelations.NEXT)
        );
    }
}

package org.behrang.telecom.controller;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.response.PhoneActivationResponse;
import org.behrang.telecom.response.PhoneNumberResponse;
import org.behrang.telecom.response.PhoneNumbersResponse;
import org.behrang.telecom.service.PhoneNumberService;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
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

@RestController
@RequestMapping(
        path = "/phone-numbers",
        consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                HalMediaType.APPLICATION_HAL_JSON_VALUE,
        },
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                HalMediaType.APPLICATION_HAL_JSON_VALUE
        }
)
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping(path = "/{phoneNumber}")
    public HttpEntity<PhoneNumberResponse> getOne(@PathVariable("phoneNumber") final String phoneNumber) {
        final var responsePayload = phoneNumberService.findByPhoneNumber(phoneNumber);

        final var response = new PhoneNumberResponse(responsePayload.content());

        addSelfLink(response, phoneNumber);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<PhoneNumbersResponse> getAll() {
        return getAll(0);
    }

    @GetMapping(params = {"pageNumber"})
    public HttpEntity<PhoneNumbersResponse> getAll(@RequestParam("pageNumber") final int pageNumber) {
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
    public HttpEntity<PhoneActivationResponse> activateOne(@PathVariable("phoneNumber") final String phoneNumber) {
        phoneNumberService.activate(phoneNumber);

        final var response = new PhoneActivationResponse(PhoneActivationResponse.STATUS_ACTIVATED);
        response.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getOne(phoneNumber))
                        .withSelfRel()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void addSelfLink(final RepresentationModel<?> response, final String phoneNumber) {
        response.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getOne(phoneNumber))
                        .withSelfRel()
        );
    }

    private void addSelfLink(final RepresentationModel<?> response, final int currentPage) {
        if (currentPage == 0) {
            response.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAll())
                            .withSelfRel()
            );
        } else {
            response.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAll(currentPage))
                            .withSelfRel()
            );
        }
    }

    private void addPrevLink(final RepresentationModel<?> response, final int currentPage) {
        response.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAll(currentPage - 1))
                        .withRel(IanaLinkRelations.PREV)
        );
    }

    private void addNextLink(final RepresentationModel<?> response, final int currentPage) {
        response.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAll(currentPage + 1))
                        .withRel(IanaLinkRelations.NEXT)
        );
    }
}

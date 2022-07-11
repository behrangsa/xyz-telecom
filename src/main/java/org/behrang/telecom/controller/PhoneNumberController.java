package org.behrang.telecom.controller;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.response.PhoneNumbersResponse;
import org.behrang.telecom.service.PhoneNumberService;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/phone-numbers")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping({"", "/"})
    public HttpEntity<PhoneNumbersResponse> list() {
        return list(0);
    }

    @GetMapping(params = {"pageNumber"})
    public HttpEntity<PhoneNumbersResponse> list(@RequestParam("pageNumber") final int pageNumber) {
        final var currentPage = phoneNumberService.findAll(pageNumber);

        final var response = new PhoneNumbersResponse(currentPage.getContent());

        addSelfLink(response, pageNumber);

        if (currentPage.isHasPrev()) {
            addPrevLink(response, pageNumber);
        }

        if (currentPage.isHasNext()) {
            addNextLink(response, pageNumber);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
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

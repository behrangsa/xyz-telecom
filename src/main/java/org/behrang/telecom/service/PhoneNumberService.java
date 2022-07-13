package org.behrang.telecom.service;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.Customer;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.exception.EntityNotFoundException;
import org.behrang.telecom.exception.PhoneNumberAlreadyActivatedException;
import org.behrang.telecom.model.CollectionPayload;
import org.behrang.telecom.model.SinglePayload;
import org.behrang.telecom.properties.PaginationProperties;
import org.behrang.telecom.repository.CustomerRepository;
import org.behrang.telecom.repository.PhoneNumberRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PaginationProperties paginationProperties;

    private final PhoneNumberRepository phoneNumberRepository;

    private final CustomerRepository customerRepository;

    public CollectionPayload<List<PhoneNumber>> findAll(final int page) {
        final int pageSize = paginationProperties.getPageSize();
        final var totalCount = phoneNumberRepository.countAll();
        final var offset = page * pageSize;
        final var hasNext = totalCount > offset + pageSize;
        final var hasPrev = page > 0;

        final var phoneNumbers = phoneNumberRepository.findAll(
                offset,
                pageSize
        );

        return new CollectionPayload<>(phoneNumbers, hasNext, hasPrev);
    }

    public CollectionPayload<List<PhoneNumber>> findAllByCustomerId(final UUID customerId, final int page) {
        final Customer customer;
        try {
            customer = customerRepository.findById(customerId);
        } catch (final EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Customer not found");
        }

        final int pageSize = paginationProperties.getPageSize();
        final var totalCount = phoneNumberRepository.countAll();
        final var offset = page * pageSize;
        final var hasNext = totalCount > offset + pageSize;
        final var hasPrev = page > 0;

        final var phoneNumbers = phoneNumberRepository.findAllByCustomerId(
                customer.getId(),
                offset,
                pageSize
        );

        return new CollectionPayload<>(phoneNumbers, hasNext, hasPrev);
    }

    public SinglePayload<PhoneNumber> findByPhoneNumber(final String phoneNumber) {
        return new SinglePayload<>(phoneNumberRepository.findByPhoneNumber(phoneNumber));
    }

    public void activate(final String phoneNumber) {
        try {
            phoneNumberRepository.findByPhoneNumber(phoneNumber);
        } catch (final EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Phone number not found");
        }

        final var rowsAffected = phoneNumberRepository.activateByPhoneNumber(phoneNumber);
        if (rowsAffected == 0) {
            throw new PhoneNumberAlreadyActivatedException("Phone number already activated");
        }
    }
}

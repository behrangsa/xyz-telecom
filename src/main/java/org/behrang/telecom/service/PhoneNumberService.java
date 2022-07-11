package org.behrang.telecom.service;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.exception.PhoneNumberNotFoundException;
import org.behrang.telecom.model.Payload;
import org.behrang.telecom.properties.PaginationProperties;
import org.behrang.telecom.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PaginationProperties paginationProperties;

    private final PhoneNumberRepository phoneNumberRepository;

    public Payload<List<PhoneNumber>> findAll(final int page) {
        final int pageSize = paginationProperties.getPageSize();
        final var totalCount = phoneNumberRepository.countAll();
        final var offset = page * pageSize;
        final var hasNext = totalCount > offset + pageSize;
        final var hasPrev = page > 0;


        final var phoneNumbers = phoneNumberRepository.findAll(
                offset,
                pageSize
        );

        return new Payload<>(phoneNumbers, hasNext, hasPrev);
    }

    public int activate(final String phoneNumber) {
        final var exists = phoneNumberRepository.existsByPhoneNumber(phoneNumber);

        if (!exists) {
            throw new PhoneNumberNotFoundException("Phone number does not exist: " + phoneNumber);
        }

        return phoneNumberRepository.activateByPhoneNumber(phoneNumber);
    }
}

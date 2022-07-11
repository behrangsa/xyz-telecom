package org.behrang.telecom.service;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.model.Page;
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

    public Page<List<PhoneNumber>> findAll(final int page) {
        final int pageSize = paginationProperties.getPageSize();
        final var totalCount = phoneNumberRepository.countAll();
        final var offset = page * pageSize;

        final var phoneNumbers = phoneNumberRepository.findAll(
                offset,
                pageSize
        );

        final boolean hasPrev = page > 0;

        final boolean hasNext = totalCount > offset + pageSize;

        return new Page<>(phoneNumbers, hasNext, hasPrev);
    }

}

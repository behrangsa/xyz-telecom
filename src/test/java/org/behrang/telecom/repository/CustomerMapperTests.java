package org.behrang.telecom.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CustomerMapperTests {

    private CustomerRepository.CustomerMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new CustomerRepository.CustomerMapper();
    }

    @Test
    void testMapRow() throws Exception {
        // Given
        final var resultSet = mock(ResultSet.class);

        when(resultSet.getString("id")).thenReturn("0914f529-9eca-4e0f-b4cd-4ada3ad432b6");
        when(resultSet.getString("name")).thenReturn("John Doe");

        // When
        final var actual = mapper.mapRow(resultSet, 0);

        // Then
        assertNotNull(actual);
        assertEquals("0914f529-9eca-4e0f-b4cd-4ada3ad432b6", actual.getId().toString());
        assertEquals("John Doe", actual.getName());
    }
}
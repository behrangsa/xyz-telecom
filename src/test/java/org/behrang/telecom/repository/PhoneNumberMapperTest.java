package org.behrang.telecom.repository;

import org.behrang.telecom.repository.PhoneNumberConstants.ColumnNames;
import org.behrang.telecom.repository.PhoneNumberRepository.PhoneNumberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneNumberMapperTest {

    private PhoneNumberMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new PhoneNumberMapper();
    }

    @Test
    void testMapRow() throws Exception {
        // Given
        final var resultSet = mock(ResultSet.class);

        when(resultSet.getString(ColumnNames.ID)).thenReturn("0014f529-9eca-4e0f-b4cd-4ada3ad432b6");
        when(resultSet.getString(ColumnNames.CUSTOMER_ID)).thenReturn("1114f529-9eca-4e0f-b4cd-4ada3ad432b6");
        when(resultSet.getString(ColumnNames.PHONE_NUMBER)).thenReturn("0901 234 567");
        when(resultSet.getBoolean(ColumnNames.IS_ACTIVE)).thenReturn(true);

        // When
        final var actual = mapper.mapRow(resultSet, 0);

        // Then
        assertNotNull(actual);
        assertEquals("0014f529-9eca-4e0f-b4cd-4ada3ad432b6", actual.getId().toString());
        assertEquals("1114f529-9eca-4e0f-b4cd-4ada3ad432b6", actual.getCustomerId().toString());
        assertEquals("0901 234 567", actual.getPhoneNumber());
        assertTrue(actual.isActive());
    }
}
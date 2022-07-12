package org.behrang.telecom.repository;


import org.behrang.telecom.AbstractIntegrationTest;
import org.behrang.telecom.TestOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Order(TestOrder.REPOSITORY)
@Sql("/ddl.sql")
@Sql("/customers.sql")
@Sql("/phone-numbers.sql")
class CustomerRepositoryTest extends AbstractIntegrationTest {

    private static final UUID EXISTING_CUSTOMER_ID = UUID.fromString("0a60ce63-dfcb-48f4-a269-a01cbb8a338c");

    private static final UUID NON_EXISTING_CUSTOMER_ID = UUID.fromString("12341234-dfcb-48f4-a269-a01cbb8a338c");

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindById_ExistingId() {
        final var customer = customerRepository.findById(EXISTING_CUSTOMER_ID);
        assertNotNull(customer);
        assertEquals(EXISTING_CUSTOMER_ID, customer.getId());
        assertEquals("Customer " + EXISTING_CUSTOMER_ID, customer.getName());
    }

    @Test
    void testFindById_MissingId() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            customerRepository.findById(NON_EXISTING_CUSTOMER_ID);
        });
    }
}
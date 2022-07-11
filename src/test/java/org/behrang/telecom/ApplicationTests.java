package org.behrang.telecom;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * The first SpringBootTest to run, to ensure the plumbing is okay, before
 * running other SpringBootTest tests.
 */
@Order(TestOrder.CONTEXT)
class ApplicationTests extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
    }

}

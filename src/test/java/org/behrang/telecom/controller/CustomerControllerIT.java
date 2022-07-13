package org.behrang.telecom.controller;

import org.behrang.telecom.test.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CustomerControllerIT extends AbstractIntegrationTest {

    @Test
    @Sql("/ddl.sql")
    void testGetAll_NonExistingCustomer() throws Exception {
        mockMvc.perform(
                        get("/customers/0f910ec8-c69f-483a-ba26-90df4af07702/phone-numbers").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.content", equalTo("Customer not found")));
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    void testGetAll_ExistingCustomer() throws Exception {
        mockMvc.perform(
                        get("/customers/0a60ce63-dfcb-48f4-a269-a01cbb8a338c/phone-numbers").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testGetAll_ExistingCustomerWithOnePage() throws Exception {
        mockMvc.perform(
                        get("/customers/ff1ca1a5-239a-42c6-aa62-0c0442b682ac/phone-numbers").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(16)));

        // TODO
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testGetAll_ExistingCustomerWithMultiplePages_FirstPage() throws Exception {
        // TODO
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testGetAll_ExistingCustomerWithMultiplePages_SecondPage() throws Exception {
        // TODO
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testGetAll_ExistingCustomerWithMultiplePages_LastPage() throws Exception {
        // TODO
    }
}
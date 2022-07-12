package org.behrang.telecom;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Order(TestOrder.CONTROLLER)
public class PhoneNumberControllerTests extends AbstractIntegrationTest {

    private static final String INACTIVE_PHONE_NUMBER = "099995";

    private static final String ACTIVE_PHONE_NUMBER = "099979";

    private static final String NON_EXISTENT_PHONE_NUMBER = "999999999";

    @Test
    @Sql("/ddl.sql")
    void testGetAll_Empty() throws Exception {
        mockMvc.perform(
                        get("/phone-numbers").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers")))
                .andExpect(jsonPath("$._links.next").doesNotExist())
                .andExpect(jsonPath("$._links.prev").doesNotExist());
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers-8.sql")
    void testGetAll_LessThanOnePage() throws Exception {
        mockMvc.perform(
                        get("/phone-numbers").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(8)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers")))
                .andExpect(jsonPath("$._links.prev").doesNotExist())
                .andExpect(jsonPath("$._links.next").doesNotExist());
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testGetAll_MultiplePages() throws Exception {
        mockMvc.perform(
                        get("/phone-numbers?pageNumber=2").contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(25)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers?pageNumber=2")))
                .andExpect(jsonPath("$._links.next.href", equalTo("http://localhost:8080/phone-numbers?pageNumber=3")))
                .andExpect(jsonPath("$._links.prev.href", equalTo("http://localhost:8080/phone-numbers?pageNumber=1")));
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testActivate_Successful() throws Exception {
        mockMvc.perform(
                        post(String.format("/phone-numbers/%s?action=activate", INACTIVE_PHONE_NUMBER)).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", equalTo("ACTIVATED")))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers/099995?action=activate")));
    }

    @Test
    @Sql("/ddl.sql")
    void testActivate_NotFound() throws Exception {
        mockMvc.perform(
                        post(String.format("/phone-numbers/%s?action=activate", NON_EXISTENT_PHONE_NUMBER)).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.content", equalTo("Phone number not found")));
    }

    @Test
    @Sql("/ddl.sql")
    @Sql("/customers.sql")
    @Sql("/phone-numbers.sql")
    void testActivate_AlreadyActivated() throws Exception {
        mockMvc.perform(
                        post(String.format("/phone-numbers/%s?action=activate", ACTIVE_PHONE_NUMBER)).contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.content", equalTo("Phone number already activated")));
    }
}

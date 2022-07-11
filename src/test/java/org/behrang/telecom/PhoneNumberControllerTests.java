package org.behrang.telecom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Order(TestOrder.CONTROLLER)
public class PhoneNumberControllerTests extends AbstractIntegrationTest {

    private MockMvc mockMvc;

    @Test
    @Sql("ddl.sql")
    void testGetAll_Empty() throws Exception {
        mockMvc.perform(get("/phone-numbers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers/")))
                .andExpect(jsonPath("$._links.next.href", equalTo("http://localhost:8080/phone-numbers?pageNumber=1")));
    }

    @Test
    @Sql("ddl.sql")
    @Sql("customers.sql")
    @Sql("phone-numbers-8.sql")
    void testGetAll_LessThanOnePage() throws Exception {
        mockMvc.perform(get("/phone-numbers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(8)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers/")))
                .andExpect(jsonPath("$._links.prev").doesNotExist())
                .andExpect(jsonPath("$._links.next").doesNotExist());
    }

    @Test
    @Sql("ddl.sql")
    @Sql("customers.sql")
    @Sql("phone-numbers.sql")
    void testGetAll_MultiplePages() throws Exception {
        mockMvc.perform(get("/phone-numbers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(25)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost:8080/phone-numbers/")))
                .andExpect(jsonPath("$._links.prev").doesNotExist())
                .andExpect(jsonPath("$._links.next.href", equalTo("http://localhost:8080/phone-numbers?pageNumber=1")));
    }

    @Test
    void testActivate_Successful() {
        Assertions.fail("TODO");
    }

    @Test
    void testActivate_NotFound() {
        Assertions.fail("TODO");
    }

    @Test
    void testActivate_AlreadyActivated() {
        Assertions.fail("TODO");
    }

    @BeforeEach
    public void setUp(final WebApplicationContext webApplicationContext, final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(
                        document("{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                )
                .build();
    }
}

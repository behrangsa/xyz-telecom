package org.behrang.telecom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    void testGetAll_Empty() throws Exception {
        mockMvc.perform(get("/phone-numbers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetAll_LessThanOnePage() throws Exception {
        mockMvc.perform(get("/phone-numbers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    void testGetAll_MultiplePages() {
        Assertions.fail("TODO");
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

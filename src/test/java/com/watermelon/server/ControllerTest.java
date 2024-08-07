package com.watermelon.server;

import com.epages.restdocs.apispec.ResourceSnippet;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@Import({MockLoginInterceptorConfig.class, MockAdminAuthorizationInterceptorConfig.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected ResourceSnippet resourceSnippet(String description) {
        return resource(
                ResourceSnippetParameters.builder()
                        .description(description)
                        .build()
        );
    }

    protected ResourceSnippet resourceSnippetAuthed(String description){

        return resource(
                ResourceSnippetParameters.builder()
                        .description(description)
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer token for authentication"))
                        .build()
        );

    }

}

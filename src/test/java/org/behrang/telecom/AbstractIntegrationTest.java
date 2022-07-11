package org.behrang.telecom;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Testcontainers
@SqlConfig(separator = ";;")
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    public static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:14");

    @SuppressWarnings("resource")
    @Container
    public static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withUsername("tester")
            .withPassword("password")
            .withExposedPorts(5432)
            .withDatabaseName("xyz");

    @DynamicPropertySource
    public static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> POSTGRES_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> POSTGRES_CONTAINER.getUsername());
        registry.add("spring.datasource.password", () -> POSTGRES_CONTAINER.getPassword());
    }

}
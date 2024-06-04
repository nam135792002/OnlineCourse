package com.springboot.courses;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Sell Online Course REST APIs",
                description = "Spring Boot Sell Online Course REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "RiNguyen",
                        email = "phuongnama32112002@gmail.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Sell Online Courses App Documentation",
                url = "https://github.com/nam135792002/OnlineCourse"
        )
)
public class BackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}

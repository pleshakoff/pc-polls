package com.parcom.polls.model.school;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SchoolServiceImplTestConfiguration {

    @Bean
    SchoolService schoolService(SchoolRepository schoolRepository) {
        return new SchoolServiceImpl(schoolRepository);
    }
}

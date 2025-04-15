package com.service2.consumer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class ObjectConfig {
	@Bean
	public ObjectMapper objectMapper() {
	    return new ObjectMapper();
	}

}

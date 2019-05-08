package org.chipmonk.smartcity.cnfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.chipmonk.smartcity")
public class ApplicationConfiguration {
	 @Bean
	    public ObjectMapper createObjectMapper() {  
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.registerModule(new JavaTimeModule());
	        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	        return mapper;
	    }
	
}
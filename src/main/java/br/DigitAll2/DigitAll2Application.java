package br.DigitAll2;

import javax.ws.rs.core.Application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@ComponentScan({"br.DigitAll2.controller, br.DigitAll2.service, br.DigitAll2.entity.*"})
@EnableJpaRepositories({"br.DigitAll2.repository"})
@EntityScan({"br.DigitAll2.entity"})
public class DigitAll2Application {

	public static void main(String[] args) {
		SpringApplication.run(DigitAll2Application.class, args);
	}
	
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	@Bean
	public ModelMapper modelmapper(){
		return new ModelMapper();
	}
}

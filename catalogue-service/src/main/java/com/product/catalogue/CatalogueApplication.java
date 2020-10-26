package com.product.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.product.catalogue.repository.ListingDomainRepository;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableMongoRepositories(basePackageClasses = ListingDomainRepository.class)
public class CatalogueApplication {


	public static void main(String[] args) {
		SpringApplication.run(CatalogueApplication.class, args);
	}

	
}

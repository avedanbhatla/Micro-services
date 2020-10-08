package com.lzb.employee.docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class DocumentationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentationAppApplication.class, args);
	}
	

}

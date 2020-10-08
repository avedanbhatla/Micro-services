package com.lzb.employee.docs.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;


import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;


@Configuration
public class SwaggerUIConfiguration {
	
	@Autowired
	private ServiceDefinitionsContext definitionContext;
	
	@Bean
	public RestTemplate configureTempalte(){
		return new RestTemplate();
	}
	
    @Primary
    @Bean
    @Lazy
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider, RestTemplate temp) {
        return () -> {          
            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            resources.clear();
            resources.addAll(definitionContext.getSwaggerDefinitions());
            return resources;
        };
    }
    
    
   /* @Bean
    public CorsConfiguration corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Allow anyone and anything access. Probably ok for Swagger spec
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/v2/api-docs", config);
        return new CorsConfiguration().applyPermitDefaultValues();
    }*/
}

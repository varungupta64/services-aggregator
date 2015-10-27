package com.exclusively.aggregator.server;

import com.exclusively.aggregator.zuul.filters.ProductPopulaityFilter;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                //This info will be used in Swagger. See realisation of ApiInfo for more details.
                .apiInfo(new ApiInfo(
                        "API Gateway for OMS",
                        "This is API gateway aggregator for Order management system.",
                        null,
                        null,
                        null,
                        null
                ))
                //Here we disable auto generating of responses for REST-endpoints
                .useDefaultResponseMessages(false)
                //Here we specify URI patterns which will be included in Swagger docs. Use regex for this purpose.
                .includePatterns("/orders.*");
    }
    @Bean
    public ProductPopulaityFilter productPopulaityFilter() {
    	return new ProductPopulaityFilter();
    }
}
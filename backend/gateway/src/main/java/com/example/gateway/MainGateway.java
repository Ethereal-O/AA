package com.example.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class MainGateway {
    public static void main(String[] args) {
        SpringApplication.run(MainGateway.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/getbookauthorservice")
                        .filters(f -> f.rewritePath("/getbookauthorservice","/"))

                        .uri("lb://GET-BOOK-AUTHOR")
                ).route(r->r.path("/**")
                        .uri("lb://OTHER-SERVICE")
                )
                .build();
    }
}

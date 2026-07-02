package com.finledger.gateway.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return route("ingestion-service-route")
                .route(path("/api/v1/ingest/**"), http())
                .before(uri("http://localhost:8081"))
                .build()
                .and(route("wallet-service-route")
                .route(path("/api/v1/accounts/**"), http())
                .before(uri("http://localhost:8082"))
                .build());
    }
}

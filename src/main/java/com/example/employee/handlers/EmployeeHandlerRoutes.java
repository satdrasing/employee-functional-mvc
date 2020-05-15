package com.example.employee.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.POST;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.nest;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class EmployeeHandlerRoutes {

    @Bean
    public RouterFunction<ServerResponse> employeeRoutes(EmployeeHandlers employeeHandlers, UploaderHandler uploaderHandler) {

        return nest(
                path("/api"), nest(
                        path("/employee"),
                                route(GET("/all"), employeeHandlers::getAllEmployees))
                        .andNest(path("/student"),
                                route(GET("/all"), employeeHandlers::getAllStudent))
                        .andNest(path("/uploader"),
                                route(POST("/image"),uploaderHandler::postImage)
                                        .andRoute(GET("/image/{id}"), uploaderHandler::getImage))
                );
    }
}

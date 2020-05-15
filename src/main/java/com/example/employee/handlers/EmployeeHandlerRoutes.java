package com.example.employee.handlers;

import com.example.employee.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.POST;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.nest;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.noContent;

@Configuration
@Slf4j
public class EmployeeHandlerRoutes {

    @Bean
    public RouterFunction<ServerResponse> employeeRoutes(EmployeeHandlers employeeHandlers,
                                                         UploaderHandler uploaderHandler) {

        return nest(
                path("/api"), nest(
                        path("/employee"),
                        route(GET("/all"), employeeHandlers::getAllEmployees))
                        .andNest(path("/student"),
                                route(GET("/all"), employeeHandlers::getAllStudent))
                        .andNest(path("/uploader"),
                                route(POST("/image"), uploaderHandler::postImage).filter(mediaTypeFilter())
                                        .andRoute(GET("/image/{id}"), uploaderHandler::getImage))
        );
    }

    public HandlerFilterFunction<ServerResponse, ServerResponse> mediaTypeFilter() {
        return (request, next) -> {
            MultipartFile multipartFile = CommonUtils.getMultipartFile(request);

            if (!multipartFile.getContentType().equals(APPLICATION_PDF_VALUE)) {
                return noContent().build();
            }
            return next.handle(request);
        };
    }


}

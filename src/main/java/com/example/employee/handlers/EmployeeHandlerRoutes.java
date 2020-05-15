package com.example.employee.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static com.example.employee.utils.CommonConst.FILE;
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
            MultipartFile multipartFile = getMultipartFile(request);

            if (!multipartFile.getContentType().equals(APPLICATION_PDF_VALUE)) {
                return noContent().build();
            }
            request.servletRequest().setAttribute(FILE, multipartFile);
            return next.handle(request);
        };
    }

    private   MultipartFile getMultipartFile(ServerRequest request) {
        var multipartHttpServletRequest = (MultipartHttpServletRequest) request.servletRequest();
        return multipartHttpServletRequest.getFile(FILE);
    }

}

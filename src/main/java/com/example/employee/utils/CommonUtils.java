package com.example.employee.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.function.ServerRequest;


public class CommonUtils {

    public static MultipartFile getMultipartFile(ServerRequest request) {
        var multipartHttpServletRequest = (MultipartHttpServletRequest) request.servletRequest();
        return multipartHttpServletRequest.getFile("file");
    }

}

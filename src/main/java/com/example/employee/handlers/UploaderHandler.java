package com.example.employee.handlers;

import com.example.employee.model.ImageFile;
import com.example.employee.repos.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;

@Controller
@RequiredArgsConstructor
public class UploaderHandler {

    final private ImageRepository imageRepository;

    public ServerResponse postImage(ServerRequest request) throws ServletException, IOException {

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request.servletRequest();
        byte[] files = multipartHttpServletRequest.getFile("file").getBytes();

        ImageFile _imageFile = ImageFile.builder()
                .image(new Binary(files))
                .build();
        imageRepository.save(_imageFile);

        return ServerResponse.created(URI.create("/api/uploader/image/" + _imageFile.getId())).build();
    }
}

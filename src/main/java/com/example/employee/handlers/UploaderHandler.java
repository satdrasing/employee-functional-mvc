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
import java.math.BigInteger;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.web.servlet.function.ServerResponse.badRequest;
import static org.springframework.web.servlet.function.ServerResponse.noContent;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Controller
@RequiredArgsConstructor
public class UploaderHandler {

    final private ImageRepository imageRepository;

    public ServerResponse postImage(ServerRequest request) throws ServletException, IOException {

        var multipartHttpServletRequest = (MultipartHttpServletRequest) request.servletRequest();
        var multipartFile = multipartHttpServletRequest.getFile("file");

        if(!multipartFile.getContentType().equals(APPLICATION_PDF_VALUE)){
            return noContent().build();
        }

        ImageFile _imageFile = ImageFile.builder()
                .image(new Binary(multipartFile.getBytes()))
                .build();

        imageRepository.save(_imageFile);

        return ServerResponse.created(URI.create("/api/uploader/image/" + _imageFile.getId())).build();
    }

    public ServerResponse getImage(ServerRequest request){

        return imageRepository.findById(new BigInteger(request.pathVariable("id")))
                .map(e->ok().contentType(APPLICATION_PDF).body(e.getImage().getData()))
                .orElse(badRequest().build());
    }
}

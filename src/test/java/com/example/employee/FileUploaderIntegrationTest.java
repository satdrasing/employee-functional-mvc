package com.example.employee;

import com.example.employee.model.ImageFile;
import com.example.employee.repos.ImageRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.example.employee.utils.CommonConst.FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FileUploaderIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void getImage_should_OK() throws IOException {

        File file = new ClassPathResource("file-sample.pdf").getFile();
        byte[] bytes = Files.readAllBytes(file.toPath());

        ImageFile imageFile = ImageFile.builder().image(new Binary(bytes)).build();
        var img = imageRepository.save(imageFile);


        var pdf = restTemplate.getForObject("http://localhost:" + port + "/api/uploader/image/" + img.getId(), byte[].class);
        assertEquals(bytes.length, pdf.length);
    }

    @Test
    public void postImage_should_OK() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("file-sample.pdf");

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE, classPathResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        var pdf = restTemplate.postForEntity("http://localhost:" + port + "/api/uploader/image", requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, pdf.getStatusCode());
    }
}

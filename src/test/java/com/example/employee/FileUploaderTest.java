package com.example.employee;

import com.example.employee.model.ImageFile;
import com.example.employee.repos.ImageRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FileUploaderTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImageRepository imageRepository;

    @Test
    public void getImage_should_ok() throws Exception {

        File file = new ClassPathResource("file-sample.pdf").getFile();
        byte[] bytes = Files.readAllBytes(file.toPath());

        ImageFile imageFile = ImageFile.builder().image(new Binary(bytes)).build();

        when(imageRepository.findById(new BigInteger("1")))
                .thenReturn(Optional.ofNullable(imageFile));

        mvc
                .perform(
                    get("/api/uploader/image/1")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_PDF))
                .andExpect(content().bytes(bytes));
    }

}

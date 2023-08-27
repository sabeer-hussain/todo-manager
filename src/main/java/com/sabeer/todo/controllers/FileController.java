package com.sabeer.todo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/single")
    public String uploadSingle(@RequestParam("image") MultipartFile file) throws IOException {
        LOGGER.info("Name : {}", file.getName());
        LOGGER.info("Content-Type : {}", file.getContentType());
        LOGGER.info("Original File Name : {}", file.getOriginalFilename());
        LOGGER.info("File Size : {}", file.getSize());

//        String extension = file.getContentType().split("/")[1];
//        InputStream inputStream = file.getInputStream();
//        try (FileOutputStream fileOutputStream = new FileOutputStream("data." + extension)) {
//            byte[] data = inputStream.readAllBytes();
//            fileOutputStream.write(data);
//        }

        return "File Test";
    }

    @PostMapping("/multiple")
    public String uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        Arrays.stream(files).forEach(file -> {
            LOGGER.info("File Name : {}", file.getOriginalFilename());
            LOGGER.info("File Type : {}", file.getContentType());
            // call service to upload files : and pass file object
//            try (FileOutputStream fileOutputStream = new FileOutputStream(file.getOriginalFilename());
//                 InputStream inputStream = file.getInputStream()) {
//                byte[] data = inputStream.readAllBytes();
//                fileOutputStream.write(data);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        });

        return "Handling multiple files";
    }

    // serving image files in response
    @GetMapping("/serve-image")
    public void serveImageHandler(HttpServletResponse response) {
        // image
        try {
            InputStream fileInputStream = new FileInputStream("images/data.png");
//            response.setContentType("image/png");
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            StreamUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

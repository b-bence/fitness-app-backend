package com.fitness.exerciseapiservice.controller;

import com.fitness.exerciseapiservice.model.Exercise;
import com.fitness.exerciseapiservice.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class ExerciseApiController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("/hello")
    public String testResult(){
        return "Hello";
    }

    @GetMapping("/all")
    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> image() throws IOException {
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                "exercise-api-service/src/main/resources/static/plank.gif"
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> returnImage(@PathVariable UUID id, HttpServletResponse response) throws IOException {
        System.out.println(id);
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                exerciseRepository.getExerciseById(id).getGifLocation()
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }
}

package com.fitness.testapiservice;

import com.fitness.testapiservice.model.Exercise;
import com.fitness.testapiservice.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class TestApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApiServiceApplication.class, args);
    }

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Bean
    @Profile("production")
    public CommandLineRunner init(){
        return args -> {

            Exercise first = Exercise.builder()
                    .description("plank")
                    .lengthInSeconds(100)
                    .gifUrl("https://media1.giphy.com/media/2UqZvQq4p9DjTBDVRc/giphy.gif")
                    .build();

            exerciseRepository.save(first);

        };
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}

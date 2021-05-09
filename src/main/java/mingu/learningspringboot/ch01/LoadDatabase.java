package mingu.learningspringboot.ch01;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner init(ChapterRepository repository) {
        return args -> Flux.just(
            Chapter.builder().name("Quick Start with Java").build(),
            Chapter.builder().name("Reactive Web with Spring Boot").build(),
            Chapter.builder().name("...and more!").build())
            .flatMap(repository::save)
            .subscribe(System.out::println);
    }
}

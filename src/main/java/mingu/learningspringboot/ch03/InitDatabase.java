package mingu.learningspringboot.ch03;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Profile("ch03")
@Configuration
public class InitDatabase {

    @Bean
    public CommandLineRunner init(ReactiveMongoTemplate template) {
        return args -> {
            template.dropCollection(Image.class).block();
            template.insert(new Image("1", "cover1.jpg")).block();
            template.insert(new Image("2", "cover2.jpg")).block();
            template.insert(new Image("3", "cover3.jpg")).block();
            template.findAll(Image.class).subscribe(image -> {
                System.out.println(image.toString());
            });
        };
    }
}

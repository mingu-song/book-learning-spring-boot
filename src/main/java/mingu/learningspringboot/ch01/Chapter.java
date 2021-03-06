package mingu.learningspringboot.ch01;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Profile("ch01")
@Data
@Builder
@Document
public class Chapter {

    @Id
    private String id;

    private String name;
}

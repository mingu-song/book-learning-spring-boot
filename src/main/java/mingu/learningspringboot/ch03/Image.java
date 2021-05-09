package mingu.learningspringboot.ch03;

import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Profile("ch03")
@Data
@Document
public class Image {

    @Id
    final private String id;

    final private String name;
}

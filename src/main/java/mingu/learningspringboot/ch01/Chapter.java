package mingu.learningspringboot.ch01;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Chapter {

    @Id
    private String id;

    private String name;
}

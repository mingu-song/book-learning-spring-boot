package mingu.learningspringboot.ch02;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;

@Profile("ch02")
@Data
@Builder
public class Image {
    private String id;
    private String name;
}

package mingu.learningspringboot.ch02;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {
    private String id;
    private String name;
}

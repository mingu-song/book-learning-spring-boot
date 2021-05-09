package mingu.learningspringboot.ch01;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Profile("ch01")
@RestController
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterRepository chapterRepository;

    @GetMapping("/chapters")
    public Flux<Chapter> listing() {
        return chapterRepository.findAll();
    }
}

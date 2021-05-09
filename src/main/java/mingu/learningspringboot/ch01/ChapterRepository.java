package mingu.learningspringboot.ch01;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Profile("ch01")
public interface ChapterRepository extends ReactiveCrudRepository<Chapter, String> {
}

package mingu.learningspringboot.ch01;

import mingu.learningspringboot.ch01.Chapter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ChapterRepository extends ReactiveCrudRepository<Chapter, String> {
}

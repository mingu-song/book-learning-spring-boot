package mingu.learningspringboot.ch02;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class ImageService {
    private static final String UPLOAD_ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    public Flux<Image> findAllImages() {
        try {
            return Flux.fromStream(Files.list(Paths.get(UPLOAD_ROOT)))
                    .map(path -> Image.builder().id(String.valueOf(path.hashCode())).name(path.getFileName().toString()).build());
        } catch (IOException e) {
            return Flux.empty();
        }
    }

    public Mono<Resource> findOneImage(String filename) {
        return Mono.fromSupplier(() ->
            resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename));
    }

    public Mono<Void> createImage(Flux<FilePart> files) {
        return files
            .flatMap(file -> file.transferTo(Paths.get(UPLOAD_ROOT, file.filename()).toFile()))
            .then();
    }

    public Mono<Void> deleteImage(String filename) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    // pre-load test images
    @Bean
    public CommandLineRunner setup() {
        return args -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectories(Paths.get(UPLOAD_ROOT));

            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/cover1.jpg"));
            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/cover2.jpg"));
            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/cover3.jpg"));
        };
    }
}

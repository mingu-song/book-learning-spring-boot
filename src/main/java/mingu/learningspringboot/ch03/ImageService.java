package mingu.learningspringboot.ch03;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
import java.util.UUID;

@Profile("ch03")
@RequiredArgsConstructor
@Service
public class ImageService {
    private static final String UPLOAD_ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    private final ImageRepository imageRepository;

    public Flux<Image> findAllImages() {
        return imageRepository.findAll().log("findAll");
    }

    public Mono<Resource> findOneImage(String filename) {
        return Mono.fromSupplier(() ->
            resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename));
    }

    public Mono<Void> createImage(Flux<FilePart> files) {
        return files
            .log("createImage-files")
            .flatMap(file -> {
                Mono<Image> saveDBImage = imageRepository.save(new Image(UUID.randomUUID().toString(), file.filename())).log("createImage-save");

                Mono<Void> copyFile = Mono.just(Paths.get(UPLOAD_ROOT, file.filename()).toFile())
                        .log("createImage-pickTarget")
                        .map(destFile -> {
                            try {
                                destFile.createNewFile();
                                return destFile;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .log("createImage-newFile")
                        .flatMap(file::transferTo)
                        .log("createImage-copy");

                return Mono.when(saveDBImage, copyFile).log("createImage-when");
            })
            .log("createImage-flatMap")
            .then()
            .log("createImage-done");
    }

    public Mono<Void> deleteImage(String filename) {
        Mono<Void> deleteDBImage = imageRepository
                .findByName(filename)
                .log("deleteImage-find")
                .flatMap(imageRepository::delete)
                .log("deleteImage-record");

        Mono<Object> deleteFile = Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).log("deleteImage-file");

        return Mono.when(deleteDBImage, deleteFile)
                .log("deleteImage-when")
                .then()
                .log("deleteImage-done");
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

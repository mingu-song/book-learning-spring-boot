package mingu.learningspringboot.ch01;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("ch01")
@RestController
public class HomeController {

    @GetMapping
    public String greeting(@RequestParam(required = false, defaultValue = "") String name) {
        return name.equals("") ? "Hey!" : "Hey, " + name + "!";
    }
}

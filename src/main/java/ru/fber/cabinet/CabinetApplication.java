package ru.fber.cabinet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class CabinetApplication {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Collections.singletonMap("login", "whatever");
    }

    public static void main(String[] args) {
        SpringApplication.run(CabinetApplication.class, args);
    }
}
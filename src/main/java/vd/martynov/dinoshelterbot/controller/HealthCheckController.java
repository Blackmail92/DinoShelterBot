package vd.martynov.dinoshelterbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<?> check() {
        return ResponseEntity.ok().build();
    }
}

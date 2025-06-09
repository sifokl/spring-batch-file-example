package com.batch.foobarquix.controller;


import com.batch.foobarquix.service.FooBarQuixTransformerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FooBarQuixController {

    private final FooBarQuixTransformerService fooBarQuixTransformerService;
    public FooBarQuixController(FooBarQuixTransformerService fooBarQuixTransformerService) {
        this.fooBarQuixTransformerService = fooBarQuixTransformerService;
    }

    @GetMapping("/transform/{number}")
    public ResponseEntity<String> transformSingleNumber(@PathVariable int number) {
        if (number < 0 || number > 100) {
            return ResponseEntity.badRequest().body("Le nombre doit être entre 0 et 100.");
        }
        return ResponseEntity.ok(fooBarQuixTransformerService.transform(number));
    }


}


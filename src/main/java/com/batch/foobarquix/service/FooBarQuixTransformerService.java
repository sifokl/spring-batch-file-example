package com.batch.foobarquix.service;

import com.batch.foobarquix.exception.InvalidNumberRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FooBarQuixTransformerService {

    private final Logger log = LoggerFactory.getLogger(FooBarQuixTransformerService.class);

    public String transform(int number) {

        log.debug("Début de la transformation pour le nombre : {}", number);

        if (number < 0 || number > 100) {
            throw new InvalidNumberRangeException(number); // Exception personnalisée
        }

        StringBuilder result = new StringBuilder();

        // divisible par N
        if (number % 3 == 0) result.append("FOO");
        if (number % 5 == 0) result.append("BAR");

        // contient Str
        for (char digit : String.valueOf(number).toCharArray()) {
            if (digit == '3') result.append("FOO");
            else if (digit == '5') result.append("BAR");
            else if (digit == '7') result.append("QUIX");
        }

        // default behavior
        result = new StringBuilder(result.length() > 0 ? result.toString() : String.valueOf(number));
        log.debug("Résultat final de la transformation pour {} : {}", number, result.isEmpty() ? "(vide)" : result);

        return result.toString();
    }


}

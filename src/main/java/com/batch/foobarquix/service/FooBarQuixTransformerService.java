package com.batch.foobarquix.service;

import org.springframework.stereotype.Service;

@Service
public class FooBarQuixTransformerService {


    public String transform(int number) {
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
        return result.length() > 0 ? result.toString() : String.valueOf(number);
    }


}

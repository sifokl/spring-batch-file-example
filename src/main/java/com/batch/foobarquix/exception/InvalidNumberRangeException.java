package com.batch.foobarquix.exception;

public class InvalidNumberRangeException extends RuntimeException {
    public InvalidNumberRangeException(int number) {
        super("Le nombre " + number + " n'est pas valide. Il doit être entre 0 et 100.");
    }
}
package com.batch.foobarquix.exception;

public class MissingBatchInputFileException extends RuntimeException {
    public MissingBatchInputFileException(String path) {
        super("Le fichier d’entrée est introuvable ou illisible : " + path);
    }
}
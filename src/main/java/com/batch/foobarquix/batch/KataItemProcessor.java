package com.batch.foobarquix.batch;


import com.batch.foobarquix.service.FooBarQuixTransformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class KataItemProcessor implements ItemProcessor<Integer, String> {

    private static final Logger log = LoggerFactory.getLogger(KataItemProcessor.class);

    private final FooBarQuixTransformerService transformer;

    public KataItemProcessor(FooBarQuixTransformerService transformer) {
        this.transformer = transformer;
    }

    @Override
    public String process(Integer item) {
        log.debug("Début du traitement de l'élément : {}", item);
        String transformed = transformer.transform(item);
        String output = item + " " + transformed;
        log.debug("Résultat après transformation : {}", output);
        return output;
    }
}
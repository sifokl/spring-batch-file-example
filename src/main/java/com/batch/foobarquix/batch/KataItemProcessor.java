package com.batch.foobarquix.batch;


import com.batch.foobarquix.service.FooBarQuixTransformerService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class KataItemProcessor implements ItemProcessor<Integer, String> {

    private final FooBarQuixTransformerService transformer;

    public KataItemProcessor(FooBarQuixTransformerService transformer) {
        this.transformer = transformer;
    }

    @Override
    public String process(Integer item) {
        String transformed = transformer.transform(item);
        return item + " " + transformed;
    }
}
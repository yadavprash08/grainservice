package com.svs.krishi.grainservice.app.di;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.svs.krishi.grainservice.app.io.ModalInputSupplier;
import com.svs.krishi.grainservice.app.io.ModalReader;
import com.svs.krishi.grainservice.app.ml.Classifiers;
import com.svs.krishi.grainservice.app.ml.KnnClassifiers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This is the primary module for initializing the di module of the service
 */
@Slf4j
public class ServiceModule extends AbstractModule {

    @Provides
    @Singleton
    Gson gson() {
        return new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    @Override
    protected void configure() {
        bind(ModalReader.class);
        bind(Classifiers.class).to(KnnClassifiers.class);
    }

    @Provides
    ModalInputSupplier modalInputSupplier(){
        return new ModalInputSupplier() {
            @Override
            public Reader get() {
                try {
                    final Path path = Paths.get("./data/wheat_type.json");
                    if(Files.notExists(path)) {
                        log.error("Missing file with dataset.");
                    }
                    return Files.newBufferedReader(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}

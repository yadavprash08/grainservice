package com.svs.krishi.grainservice.app.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import lombok.Getter;

import java.util.Objects;

/**
 * This is the factory used to create the injector for the application.
 */
public class InjectionFactory {

    @Getter
    private final Injector injector;

    private InjectionFactory() {
        this.injector = Guice.createInjector(Stage.PRODUCTION, new ServiceModule());
    }

    private static InjectionFactory factoryInstance;

    public static InjectionFactory getInstance() {
        if (Objects.isNull(factoryInstance)) {
            factoryInstance = new InjectionFactory();
        }
        return factoryInstance;
    }
}

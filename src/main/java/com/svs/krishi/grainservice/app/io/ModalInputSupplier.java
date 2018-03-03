package com.svs.krishi.grainservice.app.io;

import java.io.Reader;
import java.util.function.Supplier;

/**
 * This is the supplier class which provides the source file for creating the core database which is used for
 * classification.
 */
public interface ModalInputSupplier extends Supplier<Reader> {

}

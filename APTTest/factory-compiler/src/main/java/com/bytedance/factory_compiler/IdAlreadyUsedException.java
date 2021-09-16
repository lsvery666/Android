package com.bytedance.factory_compiler;

public class IdAlreadyUsedException extends RuntimeException {
    public IdAlreadyUsedException(FactoryAnnotatedClass factoryAnnotatedClass) {
    }
}

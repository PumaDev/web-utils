package com.au.utils.exception;

public class FactoryItemNotExistsException extends RuntimeException {
    public FactoryItemNotExistsException(Object key) {
        super(String.format("Factory Item with key %s doesn't exist. Please verify the keys", key.toString()));
    }
}

package com.au.utils.factory;

import com.au.utils.exception.FactoryItemNotExistsException;

public abstract class NotEmptyKeyFactory<V, K> extends AbstractFactory<V, K> {

    @Override
    public V getItem(K key) {
        V value = factoryItems.get(key);
        if (value == null) {
            throw new FactoryItemNotExistsException(key);
        }
        return value;
    }
}

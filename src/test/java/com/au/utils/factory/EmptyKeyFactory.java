package com.au.utils.factory;

public abstract class EmptyKeyFactory<V, K> extends AbstractFactory<V, K> {

    @Override
    public V getItem(K key) {
        return factoryItems.get(key);
    }
}

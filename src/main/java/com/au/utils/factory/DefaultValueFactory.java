package com.au.utils.factory;

import javax.annotation.PostConstruct;

public abstract class DefaultValueFactory<V, K> extends AbstractFactory<V, K> {

    protected V defaultValue;

    @PostConstruct
    public void initDefaultValue() {
        defaultValue = getDefaultValue();
    }

    protected abstract V getDefaultValue();

    @Override
    public V getItem(K key) {
        return factoryItems.getOrDefault(key, defaultValue);
    }
}

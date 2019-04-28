package com.au.utils.factory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFactory<V, K> implements Factory<V, K> {
    protected Map<K, V> factoryItems;

    @PostConstruct
    public void init() {
        factoryItems = new HashMap<>();
        factoryItems.putAll(initItems());
    }

    /**
     * For overriding
     * @return com.au.utils.factory items
     */
    protected abstract Map<K, V> initItems();

}

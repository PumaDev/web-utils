package com.au.utils.factory;

public interface Factory<V, K> {

    V getItem(K key);
}


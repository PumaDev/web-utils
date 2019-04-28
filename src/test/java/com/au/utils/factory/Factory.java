package com.au.utils.factory;

import javax.annotation.PostConstruct;

public interface Factory<V, K> {

    V getItem(K key);
}


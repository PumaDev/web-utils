package com.au.utils.convert;

import java.util.List;

public interface ViewDomainConvertService {

    public <E, V> E convertViewToDomainEntity(V view);

    public <E, V> List<E> convertViewsToDomainEntity(List<V> views);

    public <E, V> V convertDomainEntityToView(E entity, Class<V> viewClass);

    public <E, V> List<V> convertDomainEntitiesToViews(List<E> entities, Class<V> viewClass);
}

package com.au.utils.convert.object;

public interface ViewDomainConverter {

    public <V, E> V convertDomainEntityToView(E entity, Class<V> viewClass);

    public <V, E> E convertViewToDomainEntity(V view);
}

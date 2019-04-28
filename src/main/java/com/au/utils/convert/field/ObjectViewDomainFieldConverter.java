package com.au.utils.convert.field;

import org.springframework.stereotype.Component;

@Component
public class ObjectViewDomainFieldConverter implements ViewDomainFieldConverter<Object, Object> {
    @Override
    public Object convertViewFieldToDomainField(Object o) {
        return o;
    }

    @Override
    public Object convertDomainFieldToViewField(Object o) {
        return o;
    }
}

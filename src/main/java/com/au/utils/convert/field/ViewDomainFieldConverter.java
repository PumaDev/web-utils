package com.au.utils.convert.field;

public interface ViewDomainFieldConverter<F, B> {

    F convertViewFieldToDomainField(B b);

    B convertDomainFieldToViewField(F f);
}

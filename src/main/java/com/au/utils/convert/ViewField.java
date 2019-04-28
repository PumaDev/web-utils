package com.au.utils.convert;

import com.au.utils.convert.field.ObjectViewDomainFieldConverter;
import com.au.utils.convert.field.ViewDomainFieldConverter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewField {

    @AliasFor("entityFieldName")
    String value() default "";

    @AliasFor("value")
    String entityFieldName() default "";

    Class<? extends ViewDomainFieldConverter> converter() default ObjectViewDomainFieldConverter.class;
}

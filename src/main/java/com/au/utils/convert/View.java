package com.au.utils.convert;

import com.au.utils.convert.object.SimpleViewDomainConverter;
import com.au.utils.convert.object.ViewDomainConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface View  {

    Class value() default Object.class;

    Class domainClass() default Object.class;

    Class<? extends ViewDomainConverter> converter() default SimpleViewDomainConverter.class;
}

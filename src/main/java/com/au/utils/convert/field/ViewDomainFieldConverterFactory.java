package com.au.utils.convert.field;

import com.au.utils.factory.DefaultValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewDomainFieldConverterFactory extends DefaultValueFactory<ViewDomainFieldConverter, Class<? extends ViewDomainFieldConverter>> {

    private final ObjectViewDomainFieldConverter objectViewDomainFieldConverter;

    private final ApplicationContext applicationContext;

    public ViewDomainFieldConverterFactory(ObjectViewDomainFieldConverter objectViewDomainFieldConverter, ApplicationContext applicationContext) {
        this.objectViewDomainFieldConverter = objectViewDomainFieldConverter;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ViewDomainFieldConverter getDefaultValue() {
        return objectViewDomainFieldConverter;
    }

    @Override
    protected Map<Class<? extends ViewDomainFieldConverter>, ViewDomainFieldConverter> initItems() {
        return applicationContext.getBeansOfType(ViewDomainFieldConverter.class)
                .values()
                .stream()
                .collect(Collectors.toMap(ViewDomainFieldConverter::getClass, bean -> bean));

    }
}

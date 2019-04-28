package com.au.utils.convert.object;

import com.au.utils.factory.DefaultValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewDomainConvertFactory extends DefaultValueFactory<ViewDomainConverter, Class<? extends ViewDomainConverter>> {

    private final SimpleViewDomainConverter simpleViewDomainConverter;

    private final ApplicationContext applicationContext;

    public ViewDomainConvertFactory(SimpleViewDomainConverter simpleViewDomainConverter, ApplicationContext applicationContext) {
        this.simpleViewDomainConverter = simpleViewDomainConverter;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ViewDomainConverter getDefaultValue() {
        return simpleViewDomainConverter;
    }

    @Override
    protected Map<Class<? extends ViewDomainConverter>, ViewDomainConverter> initItems() { ;
        return applicationContext.getBeansOfType(ViewDomainConverter.class)
                .values()
                .stream()
                .collect(Collectors.toMap(ViewDomainConverter::getClass, bean -> bean));
    }
}

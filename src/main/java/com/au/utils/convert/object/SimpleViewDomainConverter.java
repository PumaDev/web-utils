package com.au.utils.convert.object;

import com.au.utils.convert.View;
import com.au.utils.convert.ViewField;
import com.au.utils.convert.field.ViewDomainFieldConverter;
import com.au.utils.convert.field.ViewDomainFieldConverterFactory;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

// TODO: Enhance code style
// TODO: Add ability to choose field converter
@Service
public class SimpleViewDomainConverter implements ViewDomainConverter {

    private final ViewDomainFieldConverterFactory viewDomainFieldConverterFactory;

    public SimpleViewDomainConverter(ViewDomainFieldConverterFactory viewDomainFieldConverterFactory) {
        this.viewDomainFieldConverterFactory = viewDomainFieldConverterFactory;
    }

    @Override
    public <V, E> V convertDomainEntityToView(E inputEntity, Class<V> viewClass) {
        final E entity = (E) Hibernate.unproxy(inputEntity);
        V view;
        try {
            view = viewClass.newInstance();
            Class entityClass = getEntityClass(viewClass.getAnnotation(View.class));
            if (!entityClass.isAssignableFrom(entity.getClass())) {
                throw new RuntimeException();
            }

            Arrays.stream(viewClass.getDeclaredFields())
                    .filter(field -> field.getAnnotation(ViewField.class) != null)
                    .forEach(field -> {
                        field.setAccessible(true);
                        ViewDomainFieldConverter viewDomainFieldConverter = viewDomainFieldConverterFactory.getItem(field.getAnnotation(ViewField.class).converter());
                        Object fieldValue = ReflectionUtils.getField(getField(entity, getEntityFieldNameFromViewField(field)), entity);
                        Object convertedValue = viewDomainFieldConverter.convertDomainFieldToViewField(fieldValue);
                        ReflectionUtils.setField(field, view, convertedValue);
                    });
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    @Override
    public <V, E> E convertViewToDomainEntity(V view) {
        View viewClassMetaInfo = view.getClass().getAnnotation(View.class);
        Class<E> entityClass = getEntityClass(viewClassMetaInfo);

        try {
            E entity = entityClass.newInstance();
            Arrays.stream(view.getClass().getDeclaredFields())
                    .filter(field -> field.getAnnotation(ViewField.class) != null)
                    .forEach(field -> {
                        String entityFieldName = getEntityFieldNameFromViewField(field);
                        field.setAccessible(true);
                        ViewDomainFieldConverter viewDomainFieldConverter = viewDomainFieldConverterFactory.getItem(field.getAnnotation(ViewField.class).converter());
                        Object fieldValue = ReflectionUtils.getField(field, view);
                        Object convertedValue = viewDomainFieldConverter.convertViewFieldToDomainField(fieldValue);
                        ReflectionUtils.setField(getField(entity, entityFieldName), entity, convertedValue);
                    });

            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    private <E> Class<E> getEntityClass(View viewAnnotation) {
        Class<E> entityClass = (Class<E>) viewAnnotation.value();
        if (entityClass == Object.class) {
            entityClass = viewAnnotation.domainClass();
            if (entityClass == Object.class) {
                throw new RuntimeException();
            }
        }
        return entityClass;
    }

    // TODO: may be implement some chain of responsibility with streams and lambda?
    private String getEntityFieldNameFromViewField(Field field) {
        ViewField viewFieldAnnotation = field.getAnnotation(ViewField.class);
        String fieldName = viewFieldAnnotation.value();
        if (StringUtils.isEmpty(fieldName)) {
            fieldName = viewFieldAnnotation.entityFieldName();
            if (StringUtils.isEmpty(fieldName)) {
                fieldName = field.getName();
            }
        }
        return fieldName;
    }

    private Field getField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }
}

package com.au.utils.convert;

import com.au.utils.convert.object.ViewDomainConvertFactory;
import com.au.utils.convert.object.ViewDomainConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// TODO: Add ability to select domain-view converter
@Service
public class ViewDomainConvertServiceImpl implements ViewDomainConvertService {

    private final ViewDomainConvertFactory viewDomainConvertFactory;

    public ViewDomainConvertServiceImpl(ViewDomainConvertFactory viewDomainConvertFactory) {
        this.viewDomainConvertFactory = viewDomainConvertFactory;
    }

    @Override
    public <E, V> E convertViewToDomainEntity(V view) {
        if (view == null) {
            return null;
        }
        ViewDomainConverter viewDomainConverter = getViewDomainConverter(view.getClass());
        return viewDomainConverter.convertViewToDomainEntity(view);
    }

    @SuppressWarnings("Unchecked")
    @Override
    public <E, V> List<E> convertViewsToDomainEntity(List<V> views) {
        ViewDomainConverter viewDomainConverter = views.size() > 0 ? getViewDomainConverter(views.get(0).getClass()) : null;
        return (List<E>) views.stream()
                .map(view -> view != null ? viewDomainConverter.convertViewToDomainEntity(view) : null)
                .collect(Collectors.toList());
    }

    @Override
    public <E, V> V convertDomainEntityToView(E entity, Class<V> viewClass) {
        ViewDomainConverter viewDomainConverter = getViewDomainConverter(viewClass);
        return entity != null ?viewDomainConverter.convertDomainEntityToView(entity, viewClass) : null;
    }

    @Override
    public <E, V> List<V> convertDomainEntitiesToViews(List<E> entities, Class<V> viewClass) {
        ViewDomainConverter viewDomainConverter = getViewDomainConverter(viewClass);
        return entities.stream()
                    .map(entity -> entity != null ? viewDomainConverter.convertDomainEntityToView(entity, viewClass): null)
                    .collect(Collectors.toList());
    }

    private <V> ViewDomainConverter getViewDomainConverter(Class<V> viewClass) {
        View view = viewClass.getAnnotation(View.class);
        if (view == null) {
            throw new RuntimeException();
        }
        return viewDomainConvertFactory.getItem(view.converter());
    }
}

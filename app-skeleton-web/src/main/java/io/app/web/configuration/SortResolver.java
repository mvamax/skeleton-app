package io.app.web.configuration;

import io.app.web.util.SortableUri;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SortResolver extends SortHandlerMethodArgumentResolver {

    public static final String PARAMETER_SORT = "sort";
    public static final String PARAMETER_DELIMITER = ",";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
	return Sort.class.equals(parameter.getParameterType());
    }

    @Override
    public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
	final String[] directionParameter = webRequest.getParameterValues(PARAMETER_SORT);

	// No parameter
	if (directionParameter == null) {
	    return getDefaultFromAnnotationOrFallback(parameter);
	}

	// Single empty parameter, e.g "sort="
	if (directionParameter.length == 1 && StringUtils.isBlank(directionParameter[0])) {
	    return getDefaultFromAnnotationOrFallback(parameter);
	}

	final SortableUri sortableUri = parameter.getParameterAnnotation(SortableUri.class);

	final List<Order> allOrders = new ArrayList<Sort.Order>();
	for (final String part : directionParameter) {
	    System.out.println("parse" + part);
	    if (part == null) {
		continue;
	    }

	    final String[] elements = part.split(PARAMETER_DELIMITER);
	    final Direction direction = elements.length == 0 ? null : Direction
		    .fromStringOrNull(elements[elements.length - 1]);

	    for (int i = 0; i < elements.length; i++) {

		if (i == elements.length - 1 && direction != null) {
		    continue;
		}

		final String property = elements[i];

		if (!org.springframework.util.StringUtils.hasText(property)) {
		    continue;
		}
		allOrders.add(new Order(direction, property));
	    }
	}
	System.out.println("allOrders" + allOrders);
	return allOrders.isEmpty() ? null : sortableUri.pageable().translate(new Sort(allOrders));

    }

    private Sort getDefaultFromAnnotationOrFallback(MethodParameter parameter) {

	final SortableUri sortableUri = parameter.getParameterAnnotation(SortableUri.class);

	return sortableUri.pageable().getDefaultSort();
    }

}

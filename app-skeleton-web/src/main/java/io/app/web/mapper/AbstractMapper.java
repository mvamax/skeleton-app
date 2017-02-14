package io.app.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<T, S> implements Mapper<T, S> {

    @Override
    public abstract T toView(S s);

    @Override
    public abstract S toEntity(T t);

    @Override
    public List<S> toListEntity(List<T> list) {
	return list.stream().map(t -> this.toEntity(t)).collect(Collectors.toList());
    }

    @Override
    public List<T> toListView(List<S> list) {
	return list.stream().map(s -> this.toView(s)).collect(Collectors.toList());
    }

}

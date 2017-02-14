package io.app.web.mapper;

import java.util.List;

public interface Mapper<T, S> {

    T toView(S s);

    S toEntity(T t);

    List<S> toListEntity(List<T> list);

    List<T> toListView(List<S> list);

}

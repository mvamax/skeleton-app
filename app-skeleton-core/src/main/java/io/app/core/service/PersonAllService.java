package io.app.core.service;

import io.app.core.domain.PersonAllView;

import java.util.List;

public interface PersonAllService {

    List<PersonAllView> findAllUnionWithJpa();

    List<PersonAllView> findAllUnionWithQUeryDsl();

    List<? extends PersonAllView> findAllUnionWithJpa(Long type);

}

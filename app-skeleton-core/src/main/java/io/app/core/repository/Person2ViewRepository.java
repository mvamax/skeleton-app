package io.app.core.repository;

import io.app.core.domain.Person2View;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Person2ViewRepository extends JpaRepository<Person2View, Long>, JpaSpecificationExecutor<Person2View> {

}
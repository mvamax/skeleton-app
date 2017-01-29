package io.app.core.repository;

import io.app.core.domain.Person1View;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Person1ViewRepository extends JpaRepository<Person1View, Long>, JpaSpecificationExecutor<Person1View> {

}
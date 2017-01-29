package io.app.core.repository;

import io.app.core.domain.PersonAll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonAllRepository extends JpaRepository<PersonAll, Long>, JpaSpecificationExecutor<PersonAll> {

}
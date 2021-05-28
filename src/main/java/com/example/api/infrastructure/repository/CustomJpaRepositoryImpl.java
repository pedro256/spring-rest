package com.example.api.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.example.api.domain.repository.custom.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		// TODO Auto-generated constructor stub
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from"+getDomainClass().getName();
		T entity = manager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
		return Optional.ofNullable(entity);
	}


	
}

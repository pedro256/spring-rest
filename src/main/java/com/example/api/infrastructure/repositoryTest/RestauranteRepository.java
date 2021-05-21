package com.example.api.infrastructure.repositoryTest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.example.api.domain.models.Restaurante;
import com.example.api.domain.repository.IRestauranteRepository;

@Component
public class RestauranteRepository {

	@PersistenceContext
	private EntityManager manager;

	public List<Restaurante> todos() {
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante",Restaurante.class);
		return query.getResultList();
	}

	public Restaurante buscarPorId(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = this.buscarPorId(restaurante.getId());
		if(restaurante==null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(restaurante);
		
	}

}

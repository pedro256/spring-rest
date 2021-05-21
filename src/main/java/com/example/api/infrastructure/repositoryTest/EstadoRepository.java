package com.example.api.infrastructure.repositoryTest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.example.api.domain.models.Estado;
import com.example.api.domain.repository.IEstadoRepository;

@Component
public class EstadoRepository  {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Estado> todos() {
		TypedQuery<Estado> query = manager.createQuery("from Estado",Estado.class);
		return query.getResultList();
	}

	public Estado buscarPorId(Long id) {
		return manager.find(Estado.class, id);
	}

	@Transactional
	public Estado adicionar(Estado estado) {
		return manager.merge(estado);
	}

	@Transactional
	public void remover(Estado estado) {
		estado = this.buscarPorId(estado.getId());
		if(estado==null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(estado);
	}
	

}

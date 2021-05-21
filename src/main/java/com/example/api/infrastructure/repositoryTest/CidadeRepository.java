package com.example.api.infrastructure.repositoryTest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.example.api.domain.models.Cidade;
import com.example.api.domain.repository.ICidadeRepository;
@Component
public class CidadeRepository {

	@PersistenceContext
	private EntityManager manager;

	public List<Cidade> todos() {
		TypedQuery<Cidade> query = manager.createQuery("from Cidade",Cidade.class);
		return query.getResultList();
	}

	public Cidade buscarPorId(Long id) {
		return manager.find(Cidade.class, id);
	}

	@Transactional
	public Cidade adicionar(Cidade cidade) {
		return manager.merge(cidade);
	}

	@Transactional
	public void remover(Long id ) {
	    Cidade cidade = this.buscarPorId(id);
		if(cidade==null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cidade);
		
	}

}

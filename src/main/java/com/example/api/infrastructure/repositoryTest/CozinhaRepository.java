package com.example.api.infrastructure.repositoryTest;
/*
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.example.api.domain.models.Cozinha;
import com.example.api.domain.repository.ICozinhaRepository;
@Component
public class CozinhaRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> todos(){
		TypedQuery<Cozinha> query =  manager.createQuery("from Cozinha",Cozinha.class);
		return query.getResultList();
	}
	
	@Override
	public Cozinha buscarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	//metodo adicionar pode ser usado para atualizar dados
	@Override
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = buscarPorId(id);
		if(cozinha==null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
	}

	@Override
	public List<Cozinha> buscarPorNome(String nome) {
		return manager.createQuery("from Cozinha where nome like :nome",Cozinha.class)
				.setParameter("nome","%"+nome+"")
				.getResultList();
	}

}
*/
package com.example.api.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.api.domain.models.Restaurante;
import com.example.api.domain.repository.costumers.RestauranteRepositoryCostumer;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryCostumer {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal){
		/*
		String jpql = "from Restaurante where nome like :nome and taxaFrete between :txInicial and :txFinal";
		return manager.createQuery(jpql,Restaurante.class)
				.setParameter("nome", "%"+nome+"%")
				.setParameter("taxaInicial", txFreteInicial)
				.setParameter("taxaFinal", txFreteFinal)
				.getResultList();
				*/
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = criteriaBuilder.createQuery(Restaurante.class);
		Root<Restaurante> root =  criteria.from(Restaurante.class); // FROM RESTAURANTE
		//root é a raiz da pesquisa, no caso restaurante
		
		//usamos para criar expressões como LIKE
		Predicate nomePredicado = criteriaBuilder.like(root.get("nome"), "%"+nome+"%");
		// expressão maior ou igual 
		Predicate taxaInicialW = criteriaBuilder.greaterThanOrEqualTo(root.get("taxa_frete"), taxaInicial);
		// expressão menor ou igual
		Predicate taxaFinalW= criteriaBuilder.lessThanOrEqualTo(root.get("taxa_frete"), taxaFinal);
		//inserimos a expressão no where
		criteria.where(nomePredicado,taxaInicialW,taxaFinalW);
		
		TypedQuery<Restaurante> query =  manager.createQuery(criteria);
		return query.getResultList();
	}
}

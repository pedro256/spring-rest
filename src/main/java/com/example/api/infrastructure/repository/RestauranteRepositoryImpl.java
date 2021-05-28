package com.example.api.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.api.domain.models.Restaurante;

@Repository
public class RestauranteRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;
	
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
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			//usamos para criar expressões como LIKE
			predicates.add(criteriaBuilder.like(root.get("nome"), "%"+nome+"%"));
		}
		if(taxaInicial != null) {
			// expressão maior ou igual 
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxa_frete"), taxaInicial));
		}
		if(taxaFinal != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("taxa_frete"), taxaFinal));
		}
		
		//inserimos a expressão no where
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query =  manager.createQuery(criteria);
		return query.getResultList();
	}
}

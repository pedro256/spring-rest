package com.example.api.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.domain.models.Restaurante;
import com.example.api.domain.repository.custom.CustomJpaRepository;
@Repository
public interface IRestauranteRepository extends CustomJpaRepository<Restaurante,Long>{

	/*
	List<Restaurante> todos();
	Restaurante buscarPorId(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	*/
	//valores entre uma determinação de pontos ** Between **, valores entre dois valores
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	//buscar por id da cozinha
	List<Restaurante> findByCozinhaId(Long id);
	//buscar por nome de cozinha
	List<Restaurante> findByCozinhaNome(String nome);
	//buscar por nome de cozinha com like 
	List<Restaurante> findByCozinhaNomeContaining(String nome);
	//buscar por nome de cozinha com like POR JPQL
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	//List<Restaurante> procuraCustomizadaPorNome(String nome,@Param("id") Long cozinhaId);
	
	Optional<Restaurante> findFirstByCozinhaNomeContaining(String nome);
	
	boolean existsByNome(String nome);
	
	int countByCozinhaId(Long id);
	
	//public List<Restaurante> find(String nome, BigDecimal txFreteInicial,BigDecimal txFreteFinal);
}

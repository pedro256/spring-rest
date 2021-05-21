package com.example.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.domain.models.Cozinha;

@Repository
public interface ICozinhaRepository extends JpaRepository<Cozinha,Long>{
	
	/*
	List<Cozinha> todos();
	Cozinha buscarPorId(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Long id);
	List<Cozinha> buscarPorNome(String nome);
	*/
	
	
}

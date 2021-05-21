package com.example.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.models.Cidade;

public interface ICidadeRepository extends JpaRepository<Cidade,Long>{
	
	/*
	List<Cidade> todos();
    Cidade buscarPorId(Long id);
	Cidade adicionar(Cidade cidade);
	void remover(Long id);
	*/
	//List<Cidade> findTodosByName(String nome);
	List<Cidade> nome(String nome);
	//Optional<Cidade> findByName(String name);
	
	//para a implementação do Like deve-se colocar a especificação ** Containing **
	List<Cidade> findByNomeContaining(String nome);
}

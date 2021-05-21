package com.example.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.models.Estado;

public interface IEstadoRepository extends JpaRepository<Estado,Long> {

	/*
	List<Estado> todos();
	Estado buscarPorId(Long id);
	Estado adicionar(Estado estado);
	void remover(Estado estado);
	*/
}

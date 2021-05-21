package com.example.api.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.api.domain.exception.EntidadeEmUsoException;
import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Cozinha;
import com.example.api.domain.repository.ICozinhaRepository;

@Service
public class CozinhaService {
	
	@Autowired
	private ICozinhaRepository cozinhaRepository;
	
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	} 
	public void excluir(Long id) {
		try {
			Optional<Cozinha> cozinha  = cozinhaRepository.findById(id);
			if(cozinha.isPresent()) {
				cozinhaRepository.delete(cozinha.get());
			}else {
				throw new EntidadeNaoEncontrada(
						String.format("Não existe um cadastro de cozinha com código %d. ", id)
						);
			}
			
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser remover, pois está em uso . ", id)
					);
		}
	}
	public Optional<Cozinha> buscarId(Long id) {
		return cozinhaRepository.findById(id);
	}
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	
}

package com.example.api.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.api.domain.exception.EntidadeEmUsoException;
import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Estado;
import com.example.api.domain.repository.IEstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private IEstadoRepository estadoRepository;
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	public Optional<Estado> buscarId(Long id) {
		return estadoRepository.findById(id);
	}
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	public void excluir(Long id) {
		try {
			
		    Optional<Estado> estado = this.buscarId(id);
			if(estado.isEmpty()) {
				throw new EntidadeNaoEncontrada(
						String.format("Não existe um cadastro de estado com código %d. ", id)
						);
			}
			estadoRepository.delete(estado.get());
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser remover, pois está em uso . ", id)
					);
		}
	}
	public Estado atualizar(Long id, Estado estado) {
		Optional<Estado> estadoValidado = estadoRepository.findById(id);
		if(estadoValidado.isEmpty()) {
			throw new EntidadeNaoEncontrada(
					String.format("não existe entidade estado com o código %d",id)
					);
		}
		BeanUtils.copyProperties(estado, estadoValidado.get(),"id");
		return estadoRepository.save(estadoValidado.get());
	}
}

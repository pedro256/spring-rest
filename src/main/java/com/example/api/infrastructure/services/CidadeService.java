package com.example.api.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.api.domain.exception.EntidadeEmUsoException;
import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Cidade;
import com.example.api.domain.models.Estado;
import com.example.api.domain.repository.ICidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private ICidadeRepository cidadeRepository;
	@Autowired
	private EstadoService estadoService;

	public List<Cidade> todos() {
		return cidadeRepository.findAll();
	}

	public Optional<Cidade> buscar(Long id) {
		return cidadeRepository.findById(id);
	}

	public Cidade salvar(Cidade cidade) {
		Estado estadoCidade = cidade.getEstado();
		if (estadoCidade == null) {
			throw new EntidadeNaoEncontrada("não existe entidade estado");
		}
		Long idEstado = estadoCidade.getId();
		if (estadoService.buscarId(idEstado).isEmpty()) {
			throw new EntidadeNaoEncontrada(String.format("não existe entidade estado com id %d", idEstado));
		}

		return cidadeRepository.save(cidade);

	}

	public Cidade atualizar(Long id, Cidade cidade) {
		Optional<Cidade> cidadePesquisa = cidadeRepository.findById(id);
		if (cidadePesquisa.isEmpty()) {
			throw new EntidadeNaoEncontrada(String.format("não existe entidade cidade com id %d", id));
		}
		BeanUtils.copyProperties(cidade, cidadePesquisa.get(), "id");
		return this.salvar(cidadePesquisa.get());
	}

	public void deletar(Long id) {
	
		try {
			Optional<Cidade> cidade  = cidadeRepository.findById(id);
			if(cidade.isPresent()) {
				cidadeRepository.delete(cidade.get());
			}else {
				throw new EntidadeNaoEncontrada(
						String.format("Não existe um cadastro de cidade com código %d. ", id)
						);
			}
			
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser remover, pois está em uso . ", id)
					);
		}
	}
	public List<Cidade> buscarNome(String nome){
		return cidadeRepository.nome(nome);
	}
	public List<Cidade> buscarNomeContaining(String nome){
		return cidadeRepository.findByNomeContaining(nome);
	}
}

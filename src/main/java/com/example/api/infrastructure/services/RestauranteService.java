package com.example.api.infrastructure.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Cozinha;
import com.example.api.domain.models.Restaurante;
import com.example.api.domain.repository.IRestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private IRestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Optional<Cozinha> cozinha = cozinhaService.buscarId(restaurante.getCozinha().getId());
		if(cozinha.isEmpty()) {
			throw new EntidadeNaoEncontrada(
					String.format("não existe entidade cozinha com o código %d",restaurante.getCozinha().getId() )
					);
		}
		
		restaurante.setCozinha(cozinha.get());
		return restauranteRepository.save(restaurante);
	}
	
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	public List<Restaurante> buscarEntreDoisValoresTaxa(BigDecimal tx1,BigDecimal tx2){
		return restauranteRepository.findByTaxaFreteBetween(tx1, tx2);
	}
	public List<Restaurante> buscarPorCozinhaId(Long id){
		return restauranteRepository.findByCozinhaId(id);
	}
	public List<Restaurante> buscarPorCozinhaNome(String nome){
		return restauranteRepository.findByCozinhaNome(nome);
	}
	public List<Restaurante> buscarPorCozinhaNomeContaining(String nome){
		return restauranteRepository.findByCozinhaNomeContaining(nome);
	}
	public Optional<Restaurante> buscar(Long id) {
		return restauranteRepository.findById(id);
	}
	
	public void atualizar(Long id, Restaurante restaurante) {
		Optional<Restaurante> restaurantePesquisa = this.buscar(id);
		if(restaurantePesquisa.isEmpty()) {
			throw new EntidadeNaoEncontrada(
					String.format("não existe entidade restaurante com o código %d",id)
					);
		}
		BeanUtils.copyProperties(restaurante, restaurantePesquisa.get(),"id");
		this.salvar(restaurantePesquisa.get());
	}
	
	
	
}

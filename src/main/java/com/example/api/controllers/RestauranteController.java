package com.example.api.controllers;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Restaurante;
import com.example.api.infrastructure.services.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar(){
		return ResponseEntity.ok(restauranteService.listar());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id){
		Optional<Restaurante> restaurante = restauranteService.buscar(id);
		if(restaurante.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(restaurante.get());
	}
	@GetMapping("/taxa-entre")
	public ResponseEntity<List<Restaurante>> listarPorTaxaEntre(BigDecimal initialValue,BigDecimal finalValue){
		return ResponseEntity.ok(restauranteService.buscarEntreDoisValoresTaxa(initialValue, finalValue));
	}
	@GetMapping("/cozinha/{id}")
	public ResponseEntity<List<Restaurante>> listCozinhaId(@PathVariable Long id){
		return ResponseEntity.ok(restauranteService.buscarPorCozinhaId(id));
	}
	@GetMapping("/cozinha-nome")
	public ResponseEntity<List<Restaurante>> listCozinhaNome(String nome){
		return ResponseEntity.ok(restauranteService.buscarPorCozinhaNome(nome));
	}
	@GetMapping("/cozinha-nome-containing")
	public ResponseEntity<List<Restaurante>> listCozinhaNomeContaining(String nome){
		return ResponseEntity.ok(restauranteService.buscarPorCozinhaNomeContaining(nome));
	}
	@PostMapping
	public ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante){
		try {
			restaurante =  restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontrada e) {
			return ResponseEntity.badRequest().build();
		}
		
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){
		try {
			restauranteService.atualizar(id, restaurante);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontrada e) {
			return ResponseEntity.badRequest().build();
		} 
	}

	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizacaoParcial (@PathVariable Long id, @RequestBody Map<String, Object> restauranteMap){
		Optional<Restaurante> restaurantePesquisa = restauranteService.buscar(id);
		if(restaurantePesquisa.isEmpty()) return ResponseEntity.notFound().build();
		merger(restauranteMap,restaurantePesquisa.get());
		return  atualizar(id, restaurantePesquisa.get());
	}
	
	//mesclar valores da objeto campo para o objeto restaurante
	private void merger(Map<String ,Object> campos,Restaurante restaurante) {
	//transforma objetos json em objetos java	
		ObjectMapper objMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objMapper.convertValue(campos, Restaurante.class);
		//foreach é um tipo de looping
		campos.forEach((nomePropriedade,valorPropriedade)->{
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			//transforma cada atributo em objeto especifico
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restaurante, novoValor);
			
		});
	}
}

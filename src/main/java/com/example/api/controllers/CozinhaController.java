package com.example.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.exception.EntidadeEmUsoException;
import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Cozinha;
import com.example.api.infrastructure.services.CozinhaService;


//EXEMPLO:  http://localhost:8080/cozinha
// @Controller //serve para dizer que a classe é um controller
@RestController
@RequestMapping("/cozinha") // informa qual a URI do controller
public class CozinhaController {

	
	@Autowired
	private CozinhaService cozinhaService;
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaService.listar();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
	   Optional<Cozinha> pesquisa = cozinhaService.buscarId(id);
	   if(pesquisa.isPresent()) {
		   //return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		   return ResponseEntity.ok(pesquisa.get());
	   }
	   return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha){
		return cozinhaService.salvar(cozinha);
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha){
		Optional<Cozinha> cozinhaAtual = cozinhaService.buscarId(id);
		if(cozinhaAtual.isPresent()) {
			// o terceiro parametro pra frente é variaveis para serem ignoradas 
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(),"id");
			cozinhaService.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaAtual.get());
		}
		return ResponseEntity.notFound().build();
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> delete(@PathVariable Long id){
		try {
			cozinhaService.excluir(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontrada e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	}

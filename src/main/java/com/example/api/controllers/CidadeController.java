package com.example.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Cidade;
import com.example.api.infrastructure.services.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<Cidade>> listar(){
		return ResponseEntity.ok(cidadeService.todos());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id){
		Optional<Cidade> cidade = cidadeService.buscar(id);
		if(cidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cidade.get());
	}
	@GetMapping("/query")
	public ResponseEntity<List<Cidade>> buscaNome(@RequestParam("nome") String nome){
		return ResponseEntity.ok(cidadeService.buscarNome(nome));
	}
	
	@PostMapping
	public ResponseEntity<Cidade> cadastrar(@RequestBody Cidade cidade){
		
		try {
			cidade = cidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.badRequest().build();
		}catch (EntidadeNaoEncontrada e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<Cidade> atualizar(@RequestBody Cidade cidade, @PathVariable Long id){
		try {
			cidadeService.atualizar(id, cidade);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontrada e) {
			return ResponseEntity.notFound().build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> deletar(@PathVariable Long id){
		try {
			cidadeService.deletar(id);
			return ResponseEntity.noContent().build();
		}catch ( EntidadeNaoEncontrada e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}

package com.example.api.controllers;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.exception.EntidadeEmUsoException;
import com.example.api.domain.exception.EntidadeNaoEncontrada;
import com.example.api.domain.models.Estado;
import com.example.api.infrastructure.services.EstadoService;

@RestController
@RequestMapping("/estado")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar(){
		return ResponseEntity.ok(estadoService.listar());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id){
		Optional<Estado> estado = estadoService.buscarId(id);
		if(estado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(estado.get());
	}
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado){
		estado = estadoService.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estado);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long id){
		try {
			estadoService.atualizar(id, estado);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontrada e) {
			// TODO: handle exception
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> deletar(@PathVariable Long id){
		try {
			estadoService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch ( EntidadeNaoEncontrada e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}

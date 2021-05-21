package com.example.api.domain.exception;

public class EntidadeNaoEncontrada extends RuntimeException{

	private static final long serialVersionUID =1l;
	
	public EntidadeNaoEncontrada(String msg) {
		super(msg);
	}
}

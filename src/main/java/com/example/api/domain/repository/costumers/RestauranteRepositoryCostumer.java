package com.example.api.domain.repository.costumers;

import java.math.BigDecimal;
import java.util.List;

import com.example.api.domain.models.Restaurante;

public interface RestauranteRepositoryCostumer {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

}
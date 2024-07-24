package com.marcelo.gestao.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.marcelo.gestao.domain.dto.factory.CategoriaDTOFactory;
import com.marcelo.gestao.domain.dto.factory.PaisDTOFactory;
import com.marcelo.gestao.domain.dto.nested.CategoriaDTO;
import com.marcelo.gestao.domain.dto.nested.PaisDTO;
import com.marcelo.gestao.domain.dto.nested.PaisFonteDTO;
import com.marcelo.gestao.repository.CategoriaRepository;
import com.marcelo.gestao.service.client.GenericClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DominioService {
	
	private final GenericClient client;
	
	private final CategoriaRepository categoriaRepository;
	
	@Cacheable(value = "dominio-paises")
	public List<PaisDTO> buscarPaises() {

		List<PaisFonteDTO> retorno = client.getLista("/all?fields=ccn3,translations");

		return PaisDTOFactory.converterParaLista(retorno);
	}

	@Cacheable(value = "dominio-categorias")
	public List<CategoriaDTO> buscarCategorias() {

		return CategoriaDTOFactory.converterParaLista(categoriaRepository.findAll());
	}
	
	@CacheEvict(allEntries = true, cacheNames = {"dominio-paises", "dominio-categorias"})
	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void cacheEvict() {
	}

}

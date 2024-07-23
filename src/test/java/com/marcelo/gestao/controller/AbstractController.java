package com.marcelo.gestao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.marcelo.gestao.repository.FornecedorRepository;
import com.marcelo.gestao.repository.ProdutoRepository;

public abstract class AbstractController implements BaseControllerIT {

	@Autowired
	protected TestRestTemplate restTemplate;
	
	@Autowired
	protected FornecedorRepository fornecedorRepository;
	
	@Autowired
	protected ProdutoRepository produtoRepository;


}
package br.com.produtoToy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.produtoToy.domains.Produto;
import br.com.produtoToy.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	public Optional<Produto> buscarPorId(Integer id){
		return repo.findById(id);
	}

}

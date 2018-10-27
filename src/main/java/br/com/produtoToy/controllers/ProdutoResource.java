package br.com.produtoToy.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.produtoToy.domains.Produto;
import br.com.produtoToy.repositories.ProdutoRepository;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository repo;
	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam("page")int page,@RequestParam("size")int size) {
		Page<Produto> produtos = repo.findAll(PageRequest.of(page, size));
				return ResponseEntity.ok(produtos);
	}
	
	@GetMapping(value="{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Optional<Produto> produtoOptional = repo.findById(id);
		if (produtoOptional.isPresent()) {
			return ResponseEntity.ok(produtoOptional);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	@GetMapping(value="like/{descricao}")
	public ResponseEntity<?> findByDescricao(@PathVariable String descricao) {
		List<Produto> produtos = repo.findByDescricaoContainingIgnoreCase(descricao);
		if (produtos.size()>0) {
			return ResponseEntity.ok(produtos);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping(value="{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		
		try {
			repo.deleteById(id);
			return ResponseEntity.ok(id);
		}catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
		
		
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Produto produto){
		Produto p = repo.save(produto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping
	public ResponseEntity<?> atualizar(@Valid @RequestBody Produto produto){
		Optional<Produto> p = repo.findById(produto.getId());
		if(p.isPresent()) {
			repo.save(produto);
			return ResponseEntity.noContent().build();
		}else {
		return ResponseEntity.notFound().build();
		}
	}
}

package br.com.zup.casadocodigo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.casadocodigo.model.Livro;
import br.com.zup.casadocodigo.request.NovoLivroRequest;
import br.com.zup.casadocodigo.response.DetalhesLivroResponse;
import br.com.zup.casadocodigo.response.LivroResponse;

@RestController
@RequestMapping("/livro")
public class LivroContoller {
	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping
	@Transactional
	public String salvar(@RequestBody @Valid NovoLivroRequest request) {
		Livro livro = request.toModel(manager);
		manager.persist(livro);
		return livro.toString();
	}
	
    @GetMapping
    @Transactional(readOnly = true)
    public List<LivroResponse> listar() {
        return manager.createQuery("select l from Livro l", Livro.class)
                .getResultStream()
                .map(LivroResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscar(@PathVariable Long id) {
    Livro livro = Optional.ofNullable(manager.find(Livro.class, id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        DetalhesLivroResponse detalhesLivroResponse = new DetalhesLivroResponse(livro); 
        return ResponseEntity.ok(detalhesLivroResponse);

    }
}

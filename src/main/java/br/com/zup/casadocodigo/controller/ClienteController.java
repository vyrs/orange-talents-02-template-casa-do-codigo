package br.com.zup.casadocodigo.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.casadocodigo.model.Cliente;
import br.com.zup.casadocodigo.request.NovoClienteRequest;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping
	@Transactional
	public String salvar(@RequestBody @Valid NovoClienteRequest request) {
		Cliente cliente = request.toModel(manager);
		manager.persist(cliente);
		return cliente.toString();
	}
}

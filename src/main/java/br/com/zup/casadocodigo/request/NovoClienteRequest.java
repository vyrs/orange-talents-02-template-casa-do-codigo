package br.com.zup.casadocodigo.request;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.zup.casadocodigo.model.Cliente;
import br.com.zup.casadocodigo.model.Estado;
import br.com.zup.casadocodigo.model.Pais;
import br.com.zup.casadocodigo.validacao.CPFOrCNPJ;
import br.com.zup.casadocodigo.validacao.ExistsId;
import br.com.zup.casadocodigo.validacao.UniqueValue;

public class NovoClienteRequest {
	
    @NotBlank
    @Email
    @UniqueValue(domainClass = Cliente.class, fieldName = "email", message = "Email já cadastrado!")
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    @CPFOrCNPJ
    @UniqueValue(domainClass = Cliente.class, fieldName = "documento", message = "Documento já cadastrado!")
    private String documento;

    @NotBlank
    private String endereco;
    
    @NotBlank
    private String complemento;

    @NotBlank
    private String cidade;

    @NotNull
    @ExistsId(domainClass = Pais.class, fieldName = "id", message = "Este pais não existe no banco de dados!")
    private Long paisId;
    
    private Long estadoId;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

	public NovoClienteRequest(@NotBlank @Email String email, @NotBlank String nome, @NotBlank String sobrenome,
			@NotBlank String documento, @NotBlank String endereco, @NotBlank String complemento,
			@NotBlank String cidade, @NotNull Long paisId, Long estadoId, @NotBlank String telefone,
			@NotBlank String cep) {
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.documento = documento;
		this.endereco = endereco;
		this.complemento = complemento;
		this.cidade = cidade;
		this.paisId = paisId;
		this.estadoId = estadoId;
		this.telefone = telefone;
		this.cep = cep;
	}

	public Cliente toModel(EntityManager manager) {
        @NotNull Pais pais = manager.find(Pais.class, this.paisId);
        
        Estado estado = this.estadoId != null ? manager.find(Estado.class, this.estadoId) : null;


        if(this.estadoId != null) {    
        	Assert.state(estado != null, "Você está tentando cadastrar um estado que não existe para este pais!");
        	Assert.state(pais.getId() == estado.getPais().getId(), "Esse estado não pertence a entes pais!");
        } else {
            Assert.state(manager.find(Estado.class, this.paisId) == null, "Este pais possui estados cadastrados, selecione um!");
        }
        

        return new Cliente(this.email, this.nome, this.sobrenome, this.documento, this.endereco,
                this.complemento, this.cidade, pais, estado, this.telefone, this.cep);
	}
    
	
    
}

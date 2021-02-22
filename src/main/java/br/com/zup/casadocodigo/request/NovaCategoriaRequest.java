package br.com.zup.casadocodigo.request;

import javax.validation.constraints.NotBlank;

import br.com.zup.casadocodigo.model.Categoria;
import br.com.zup.casadocodigo.validacao.UniqueValue;

public class NovaCategoriaRequest {
	
    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "Esta categoria já está cadastrada!")
    private String nome;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

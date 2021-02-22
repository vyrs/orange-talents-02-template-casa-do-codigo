package br.com.zup.casadocodigo.request;

import javax.validation.constraints.NotBlank;

import br.com.zup.casadocodigo.model.Pais;
import br.com.zup.casadocodigo.validacao.UniqueValue;

public class PaisRequest {
	
    @NotBlank
    @UniqueValue(domainClass = Pais.class, fieldName = "nome", message = "Este pais já está cadastrado no banco de dados!")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

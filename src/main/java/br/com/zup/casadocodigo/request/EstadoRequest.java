package br.com.zup.casadocodigo.request;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.zup.casadocodigo.model.Estado;
import br.com.zup.casadocodigo.model.Pais;
import br.com.zup.casadocodigo.validacao.ExistsId;


public class EstadoRequest {

	@NotBlank
	private String nome;
	
	@NotNull
	@ExistsId(domainClass = Pais.class, fieldName = "id", message = "Este pais não existe no banco de dados!")
    private Long paisId;

	public EstadoRequest(@NotBlank String nome, @NotNull Long idPais) {
		this.nome = nome;
		this.paisId = idPais;
	}

	public String getNome() {
		return nome;
	}

	public Long getPaisId() {
		return paisId;
	}
	
    public Estado toModel(EntityManager manager) {
        @NotNull Pais pais = manager.find(Pais.class, this.paisId);
        
        Assert.state(pais != null, "Você está tentando cadastrar esse estado em um pais que não existe no banco de dados!");

        return new Estado(nome, pais);
    }
}

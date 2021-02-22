package br.com.zup.casadocodigo.response;

import br.com.zup.casadocodigo.model.Autor;

public class DetalhesAutorResponse {
	private String nome;
	private String descricao;
	
	public DetalhesAutorResponse(Autor autor) {
		this.nome = autor.getNome();
		this.descricao = autor.getDescricao();
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}
}

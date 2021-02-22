package br.com.zup.casadocodigo.response;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import br.com.zup.casadocodigo.model.Livro;

public class DetalhesLivroResponse {
	private String titulo;
	private DetalhesAutorResponse autor;
	private String isbn;
	private int paginas;
	private BigDecimal preco;
	private String resumo;
	private String sumario;
	private String dataPublicacao;
	
	public DetalhesLivroResponse(Livro livro) {
		this.titulo = livro.getTitulo();
		this.autor = new DetalhesAutorResponse(livro.getAutor());
		this.isbn = livro.getIsbn();
		this.paginas = livro.getPaginas();
		this.preco = livro.getPreco();
		this.resumo = livro.getResumo();
		this.sumario = livro.getSumario();
		this.dataPublicacao = livro.getDataPublicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public String getTitulo() {
		return titulo;
	}

	public DetalhesAutorResponse getAutor() {
		return autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getPaginas() {
		return paginas;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public String getResumo() {
		return resumo;
	}

	public String getSumario() {
		return sumario;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}
	
	
	
	
}

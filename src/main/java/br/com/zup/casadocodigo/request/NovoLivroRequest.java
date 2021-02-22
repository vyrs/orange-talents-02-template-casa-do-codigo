package br.com.zup.casadocodigo.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.zup.casadocodigo.model.Autor;
import br.com.zup.casadocodigo.model.Categoria;
import br.com.zup.casadocodigo.model.Livro;
import br.com.zup.casadocodigo.validacao.ExistsId;
import br.com.zup.casadocodigo.validacao.UniqueValue;

public class NovoLivroRequest {
	
	@NotBlank
    @UniqueValue(domainClass = Livro.class, fieldName = "titulo", message = "Este título já está cadastrado!")
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String resumo;

    @NotBlank
    private String sumario;

    @NotNull
    @Min(20)
    private BigDecimal preco;

    @NotNull
    @Min(100)
    private Integer paginas;

    @NotBlank
    @UniqueValue(domainClass = Livro.class, fieldName = "isbn" , message = "Este isbn já está cadastrado!")
    private String isbn;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
    private LocalDate dataPublicacao;

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id", message = "Esta categoria não existe no banco de dados!")
    private Long categoriaId;

    @NotNull
    @ExistsId(domainClass = Autor.class, fieldName = "id", message = "Este autor não existe no banco de dados!")
    private Long autorId;

	public NovoLivroRequest(@NotBlank String titulo, @NotBlank @Size(max = 500) String resumo, @NotBlank String sumario,
			@NotNull @Min(20) BigDecimal preco, @NotNull @Min(100) Integer paginas, @NotBlank String isbn,
			@NotNull Long categoriaId, @NotNull Long autorId) {
		super();
		this.titulo = titulo;
		this.resumo = resumo;
		this.sumario = sumario;
		this.preco = preco;
		this.paginas = paginas;
		this.isbn = isbn;
		this.categoriaId = categoriaId;
		this.autorId = autorId;
	}
	
	/*
	 * O setter abaixo é necessario pq o o jackson não está
	 * sendo capaz de desserializar o json com a data no construtor.
	 */
	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Livro toModel(EntityManager manager) {
		@NotNull Autor autor = manager.find(Autor.class, this.autorId);
        @NotNull Categoria categoria = manager.find(Categoria.class, this.categoriaId);
		
        Assert.state(autor!=null, "Você está querendo cadastrar um livro para um autor que não existe no banco: " + this.autorId);
        Assert.state(categoria!=null, "Você está querendo cadastrar um livro para uma categoria que não existe no banco: " + this.categoriaId);

        return new Livro(this.titulo, this.resumo, this.sumario, this.preco,
                        this.paginas, this.isbn, this.dataPublicacao, categoria, autor);
	}
}

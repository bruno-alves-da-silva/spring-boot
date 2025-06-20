package com.example.cursodsousa.libraryapi.repository;

import com.example.cursodsousa.libraryapi.model.Autor;
import com.example.cursodsousa.libraryapi.model.GeneroLivro;
import com.example.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 **/
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> referência as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPeloTituloEPreco();

    @Query(" select a from Livro as l join l.autor as a ")
    List<Autor> listarAutoresDoLivro();

    @Query(" select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
        order by l.genero
            """)
    List<String> listarGeneroAutoresBrasileiros();

    // named parameters -> parametros nomeados
    @Query(" select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomeDaPropriedade);

    // positional parameters -> parametros posicionais
    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(
            GeneroLivro generoLivro,
            String nomeDaPropriedade);

    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1 ")
    void deletarByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1 where id = ?2 ")
    void updateDataPublicacao(LocalDate novaData, UUID id);
}

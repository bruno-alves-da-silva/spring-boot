package com.example.cursodsousa.libraryapi.repository;

import com.example.cursodsousa.libraryapi.model.Autor;
import com.example.cursodsousa.libraryapi.model.GeneroLivro;
import com.example.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        var autorSalvo = repository.save(autor);

        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("ea8b622c-81f9-49d8-b7c7-e3b3ec953032");
        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(possivelAutor);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("ea8b622c-81f9-49d8-b7c7-e3b3ec953032");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("33ad0415-7b33-40f9-8d70-a66e114c0de1");
        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            repository.delete(autorEncontrado);
        }
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1970, 8, 5));

        Livro livro = new Livro();
        livro.setIsbn("20887-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O roubo da casa assombrada");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("99999-84874");
        livro2.setPreco(BigDecimal.valueOf(650));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O roubo da casa assombrada 2");
        livro2.setDataPublicacao(LocalDate.of(200, 1, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        //livroRepository.saveAll(autor.getLivros());
    }

    @Test
    //@Transactional
    void listarLivrosAutorTest(){
        UUID id = UUID.fromString("6d695fe5-dd58-49ee-bc43-11671309c4a8");
        Autor autor = repository.findById(id).orElse(null);

        autor.getLivros().forEach(System.out::println);
    }

    @Test
    void listarLivrosSemTransactionalAutorTest(){
        UUID id = UUID.fromString("6d695fe5-dd58-49ee-bc43-11671309c4a8");
        Autor autor = repository.findById(id).orElse(null);

        // Melhor forma para carregar os dados de uma lista
        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);
    }
}

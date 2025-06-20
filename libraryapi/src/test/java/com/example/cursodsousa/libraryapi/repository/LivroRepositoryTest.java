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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Ciencias");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        UUID id = UUID.fromString("1e37e837-8921-49e0-a177-13fc46c8b6e3");
        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autor = possivelAutor.get();
            //livro.setAutor(autor);

            repository.save(livro);
        }
        else{
            System.out.println("Autor " + id +  " não encontrado.");
        }
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        Livro livroSalvo = repository.save(livro);

        System.out.println(livroSalvo);

    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        livro.setAutor(autor);

        Livro livroSalvo = repository.save(livro);

        System.out.println(livroSalvo);

    }

    @Test
    void atualizarAutorDoLivroTest(){
        UUID idLivro = UUID.fromString("740d4ad0-e1e1-4b4a-a1e8-42f27f6d13d0");
        Livro livroParaAtualizar = repository.findById(idLivro).orElse(null);

        UUID idAutor = UUID.fromString("41f075e4-e7bb-4d76-8ba7-aad19310ae71");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarTest(){
        UUID id = UUID.fromString("740d4ad0-e1e1-4b4a-a1e8-42f27f6d13d0");
        repository.deleteById(id);
    }

    @Test
    void deletarCascadeTest(){
        // Foi ativado o annotation cascade da Entidade Livro
        UUID id = UUID.fromString("45d5116e-d026-44c9-9e86-53547c871964");
        repository.deleteById(id);
    }

    @Test
    @Transactional // O annotation é necessário quando você ativa o Lazy na Entidade
    void buscarLivroTest(){
        UUID id = UUID.fromString("ef5fc7c7-1d15-4af0-8da4-e79e1dd0cf02");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("O roubo da casa assombrada");

        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBNTest(){
        List<Livro> livros = repository.findByIsbn("90887-84874");

        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        String titulo = "O roubo da casa assombrada";
        BigDecimal preco = BigDecimal.valueOf(204.00);

        List<Livro> livros = repository.findByTituloAndPreco(titulo, preco);

        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloOuISBNTest(){
        String titulo = "O roubo da casa assombrada";
        String isbn = "90887-84874";

        List<Livro> livros = repository.findByTituloOrIsbn(titulo, isbn);

        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQLTest(){
        var resultado = repository.listarTodosOrdenadoPeloTituloEPreco();

        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosJPQLTest(){
        var resultado = repository.listarAutoresDoLivro();

        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivrosJPQLTest(){
        var resultado = repository.listarNomesDiferentesLivros();

        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileirosJPQLTest(){
        var resultado = repository.listarGeneroAutoresBrasileiros();

        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "dataPublicacao");

        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamQueryParamTest(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.MISTERIO, "dataPublicacao");

        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        repository.deletarByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updatePorIdTest(){
        UUID id = UUID.fromString("ef5fc7c7-1d15-4af0-8da4-e79e1dd0cf02");
        LocalDate novaData = LocalDate.of(2000, 1, 1);
        repository.updateDataPublicacao(novaData, id);
    }
}
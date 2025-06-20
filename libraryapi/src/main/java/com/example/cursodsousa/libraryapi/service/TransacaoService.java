package com.example.cursodsousa.libraryapi.service;

import com.example.cursodsousa.libraryapi.model.Autor;
import com.example.cursodsousa.libraryapi.model.GeneroLivro;
import com.example.cursodsousa.libraryapi.model.Livro;
import com.example.cursodsousa.libraryapi.repository.AutorRepository;
import com.example.cursodsousa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        UUID id = UUID.fromString("501f70d0-996c-418d-b3e9-ca33842d79ca");
        var livro = livroRepository.findById(id).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));

        // O comando .save não é necessário, pois o método está em uma Transactional
        //livroRepository.save(livro);
    }

    public void executar(){
        // Salva o Autor
        Autor autor = new Autor();
        autor.setNome("Teste Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        autorRepository.saveAndFlush(autor);
        //autorRepository.save(autor);

        // Salva o Livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Teste Livro da Francisco");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        Livro livroSalvo = livroRepository.save(livro);

        if(autor.getNome().equals("Teste Francisco")){
            throw new RuntimeException("Rollback");
        }

        System.out.println(livroSalvo);
    }
}

package com.example.cursodsousa.libraryapi.repository;

import com.example.cursodsousa.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit -> Confirmar as alterações
     * Rollback -> Cancelar as alterações
     */
    @Test
    void transacaoSimples(){
        // Salvar um livro
        // Salvar o autor
        // Alugar o livro
        // Enviar email pro locatário
        // Notificar que o livro saiu da livraria
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();
    }
}

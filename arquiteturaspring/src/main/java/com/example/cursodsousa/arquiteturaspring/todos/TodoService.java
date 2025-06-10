package com.example.cursodsousa.arquiteturaspring.todos;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private TodoRepository todoRepository;
    private TodoValidator validator;
    private MailSender mailSender;

    public TodoService(TodoRepository todoRepository, TodoValidator validator, MailSender mailSender) {
        this.todoRepository = todoRepository;
        this.validator = validator;
        this.mailSender = mailSender;
    }

    public TodoEntity salvar(TodoEntity novoTodo){
        validator.validar(novoTodo);
        return todoRepository.save(novoTodo);
    }

    public void atualizarStatus(TodoEntity todo){
        todoRepository.save(todo);
        String status = todo.getConcluido() == Boolean.TRUE ? "Concluído" : "Não Concluído";
        mailSender.enviar("Todo " + todo.getDescricao() + " foi atualizado para " + status );
    }

    public TodoEntity buscarPorId(Integer id){
        return todoRepository.findById(id).orElse(null);
    }
}

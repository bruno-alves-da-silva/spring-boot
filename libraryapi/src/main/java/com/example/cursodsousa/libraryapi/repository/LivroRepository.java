package com.example.cursodsousa.libraryapi.repository;

import com.example.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}

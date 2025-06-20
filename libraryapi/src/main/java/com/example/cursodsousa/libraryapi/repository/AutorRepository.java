package com.example.cursodsousa.libraryapi.repository;

import com.example.cursodsousa.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}

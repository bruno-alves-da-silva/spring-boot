package com.example.cursodsousa.libraryapi;

import com.example.cursodsousa.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);

		AutorRepository repository = context.getBean(AutorRepository.class);
	}
}

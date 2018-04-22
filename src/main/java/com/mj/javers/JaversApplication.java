package com.mj.javers;

import com.mj.javers.domain.Book;
import com.mj.javers.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaversApplication implements CommandLineRunner {

	private BookRepository bookRepository;

	@Autowired
	public JaversApplication(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(JaversApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		bookRepository.save(new Book(1,"Forest", "John","Times"));
		bookRepository.save(new Book(1,"Forest", "Twain","Times"));

	}
}

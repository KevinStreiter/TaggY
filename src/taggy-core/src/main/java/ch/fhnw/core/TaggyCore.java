package ch.fhnw.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class TaggyCore implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TaggyCore.class, args);

	}

	@Override
	public void run(String... arg0) throws Exception {
	}

}

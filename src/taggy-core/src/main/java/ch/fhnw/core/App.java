package ch.fhnw.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.TagsRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class App implements CommandLineRunner{
	@Autowired
	TagsRepository tagsRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		
	}

	@Override
	public void run(String... arg0) throws Exception {
		/*
		Tag tag = new Tag("Weiblich");
		System.out.println("Tag Id"+tag.getId());
		tag = tagsRepository.save(tag);
		System.out.println(("generatet pk in dp"+ tag.getId()));
		*/		
	}

}

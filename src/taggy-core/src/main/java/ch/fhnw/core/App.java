package ch.fhnw.core;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.PictureRepository;
import ch.fhnw.core.repository.TagsRepository;
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner{
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	PictureRepository picRepository;
	@Autowired
	TagsService tagServices;
	@Autowired
	PictureService picService;
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		
	}

	@Override
	public void run(String... arg0) throws Exception {

		Tag tag = new Tag("Weiblich");
		Tag tag2 = new Tag("mänlich");
		Tag tag3 = new Tag("hässlich");
		System.out.println("Tag Id"+tag.getId());
		tag = tagsRepository.save(tag);
		tagsRepository.save(tag2);
		tagsRepository.save(tag3);
		System.out.println(("generatet pk in dp"+ tag.getId()));
		for(int i = 1; i <= 50; i++){
			Picture p1 = new Picture("ch.fhnw", "schrecklich"+ i, i);
			picRepository.save(p1);
		}
		tagServices.addTagToPicture(1, "mänlich");
		tagServices.addTagToPicture(1, "Weiblich");
		tagServices.addTagToPicture(1, "hässlich");
		List<Tag> se = new ArrayList<>();
		se.add(tag); se.add(tag2);
		
		List<Picture> picstream = picService.findPictureByTagsAnd(se);
		System.out.println(picstream);

	}

}

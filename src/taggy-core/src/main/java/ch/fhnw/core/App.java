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
		for(Long i = 1L; i <= 50; i++){
			Picture p1 = new Picture( "schrecklich"+ i, i,"ich mag es","0000998");
			picRepository.save(p1);
		}
		Picture p1 = new Picture( "schrecklich"+ 022, 00000200000L,"ich mag es","0000998");
		picRepository.save(p1);
		tagServices.addTagToPicture(1L, "mänlich");
		tagServices.addTagToPicture(1L, "Weiblich");
		tagServices.addTagToPicture(1L, "hässlich");
		List<Tag> se = new ArrayList<>();
		se.add(tag); se.add(tag2);
		List<Picture> picstream = picService.findPictureByTagsAnd(se);
		System.out.println(picstream);

	
	}

}

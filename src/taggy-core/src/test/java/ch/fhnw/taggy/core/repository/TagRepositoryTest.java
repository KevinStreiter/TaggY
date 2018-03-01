package ch.fhnw.taggy.core.repository;

import static org.junit.Assert.*;

import org.junit.Before;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import ch.fhnw.core.App;
import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.PictureRepository;
import ch.fhnw.core.repository.TagsRepository;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = App.class)
public class TagRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	PictureRepository picRepository;
	@Before
	public void setup(){
		Picture pic1 = new Picture("ch.fhnw", "Kapuutes Bild", 1);
		
		Tag tag1 = new Tag("schön");
		Tag tag2 = new Tag("Landschaft");
		
		pic1.addTag(tag1);
		pic1.addTag(tag2);
		
		tagsRepository.save(tag1);
		tagsRepository.save(tag2);
		
		picRepository.save(pic1);
	}
	
	@Test
	public void testSave(){
		Tag tag = new Tag("Weiblich");
		assertNull("epmty dp",tag.getId());
		tag = tagsRepository.save(tag);
		System.out.println(tag.getTagName()+tag.getId());
		assertNotNull("generatet pk in dp", tag.getId());
	}
	@Test
	public void checkTag(){
		int count_all_Tag = (int) tagsRepository.count();
		assertEquals("Count Tags is right", 3, count_all_Tag);
	}

}

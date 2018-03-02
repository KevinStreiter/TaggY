package ch.fhnw.taggy.core.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
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
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = App.class)
public class PictureRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
@Autowired
PictureService picService;
@Autowired
TagsService tagService;

Picture pic1;
Tag tag1;
Tag tag2;

@Before
public void setup(){
	pic1 = new Picture("ch.fhnw", "Kapuutes Bild", 1);
	
	tag1 = new Tag("schön");
	tag2 = new Tag("Landschaft");
	
	pic1.addTag(tag1);
	pic1.addTag(tag2);
	
	tagService.save(tag1);
	tagService.save(tag2);
	
	picService.save(pic1);
}
@Test
public void testSave(){
	Stream<Picture> picTest = picService.findByComment("Kapuutes Bild");
	assertEquals("Picture sace Test", pic1.getId(), picTest.findFirst().get().getId());
}
@Test
public void testfindByTagsAnd(){
	List<Tag> suche = new ArrayList<>();
	suche.add(tag1);
	suche.add(tag2);
	List<Picture> foundPic=picService.findPictureByTagsAnd(suche);
	assertEquals("Picture Search and querry", pic1.getId(), foundPic.get(0).getId());
	
}
@Test
public void testFindByTagsOR(){
	
}
@Test
public void deleteTags(){
	tagService.deleteTagFromBook(pic1.getId(), tag1.getId());
	picService.findByTag_id(tag1.getId());
}

}

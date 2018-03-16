package ch.fhnw.taggy.core.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import ch.fhnw.core.App;
import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;
import ch.fhnw.taggy.core.config.TestDataBuilder;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = App.class)
@AutoConfigureTestDatabase
public class TagServiceTest{
	@Autowired
	TagsService tagService;
	@Autowired
	PictureService picService;
	TestDataBuilder testData;
	
	
	@Before
	public void setup(){
		testData = new TestDataBuilder(picService, tagService);
		testData.makeTestSituation();
	}
	@Test
	public void startUP(){
	}
	
	@Test
	public void deleteTagsOnPicture(){
		Picture testPic=testData.getTestPics().get(0);
		List<Tag> testPicTags = tagService.findByPicture(testPic);
		Tag deletedTag = testPicTags.get(0);
		assertTrue("checks return of the service",tagService.deleteTagFromPicture(testPic.getId(), deletedTag.getId()));
		Stream<Picture> foundPic = picService.findByTag_id(testPicTags.get(0).getId());
		assertNotEquals("Looks if tag is discounnecet to the picture", testPic.getId(), foundPic.findFirst().get().getId());
		
	}
	@Test
	public void deleteTagFromList(){
		Picture testPic=testData.getTestPics().get(0);
		List<Tag> tagsToDelete =tagService.findByPicture(testPic);
		int allBefore = tagService.findAll().size();
		int numberDeleted = tagsToDelete.size();
		Tag tagToDelete = tagsToDelete.get(0);
		tagService.deleteTag(tagToDelete);
		assertNull("looks if deleted tag excists",tagService.findByName(tagToDelete.getTagName()));
		assertEquals("check if you not able to find pic with deleted Tag",Optional.empty(),picService.findPictureByTag(tagToDelete).findFirst());
		assertEquals("check if picture still exist",testPic.getId(),picService.findById(testPic.getId()).getId());

		tagsToDelete.remove(0);
		tagService.deleteTagIn(tagsToDelete);
		assertEquals("Check if all Tags are Delete and not linked to a picture",Optional.empty(), picService.findPictureByTagsOr(tagsToDelete).findFirst());
		assertEquals("Check if number of Tags is correct", allBefore-numberDeleted,tagService.findAll().size());
	}

}

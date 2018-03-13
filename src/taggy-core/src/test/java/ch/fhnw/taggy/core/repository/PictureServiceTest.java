package ch.fhnw.taggy.core.repository;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PictureServiceTest {
	@Autowired
	PictureService picService;
	@Autowired
	TagsService tagService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	TestDataBuilder testData;
	@Before
	public void setup(){
		testData = new TestDataBuilder(picService, tagService);
		testData.makeTestSituation();
	}
	@Test
	public void startUp(){
	}
	@Test
	public void testFindPictureByTagsAnd(){
		Picture testPic=testData.getTestPics().get(0);
		List<Tag> tags= tagService.findByPicture(testPic);
		List<Picture> istPics = picService.findPictureByTagsAnd(tags);
		assertEquals("find Pics by Tags and function", 2, istPics.size());	
	}
	@Test
	public void testFindPictureByTagsOR(){
		Picture testPic = testData.getTestPics().get(1);
		List<Tag> tags = tagService.findByPicture(testPic);
		tags.remove(1);
		Stream<Picture> istPics = picService.findPictureByTagsOr(tags);
		Set<Picture> istListPic= istPics.collect(Collectors.toSet());
		assertEquals("find Pics by Tags Or function", 2,istListPic.size());	
	}
	@Test
	public void testFindByComments(){
		HashMap<String, Integer> commentList = testData.getDescriptionTimes();
		String comment1 = commentList.keySet().iterator().next();
		
		/**List<Picture> pics = picService.findByCommentContaining(comment1).collect(Collectors.toList());
		logger.info("Test find By Comment"+pics.get(0));
		assertEquals("Find comment like",1, pics.size());
		pics = picService.findByDescriptionContaining(comment1).collect(Collectors.toList());
		assertEquals("Find description like",1, pics.size());**/
	}
	
	
	
	

}

package ch.fhnw.taggy.core.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.TaggyCore;
import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;
import ch.fhnw.taggy.core.config.TestDataBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = TaggyCore.class)
@AutoConfigureTestDatabase
public class PictureServiceTest {
	@Autowired
	PictureService picService;
	@Autowired
	TagsService tagService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	TestDataBuilder testData;

	@Before
	public void setup() {
		testData = new TestDataBuilder(picService, tagService);
		testData.makeTestSituation();
	}

	@Test
	public void startUp() {
	}

	@Test
	public void testFindPictureByTagsAnd() {
		Picture testPic = testData.getTestPics().get(0);
		List<Tag> tags = tagService.findByPicture(testPic);
		List<Picture> istPics = picService.findPictureByTagsAnd(tags);
		assertEquals("find Pics by Tags and function", 2, istPics.size());
	}

	@Test
	public void testFindPictureByTagsOR() {

		Picture testPic = testData.getTestPics().get(1);
		List<Tag> tags = tagService.findByPicture(testPic);
		tags.remove(1);
		logger.info("Test Pics find Or tags: " + tags.toString());
		List<Picture> istPics = picService.findPictureByTagsOr(tags);
		logger.info("test find Picrue Or" + istPics.toString() + "\t" + testPic.toString());
		assertEquals("find Pics by Tags Or function", 2, istPics.size());
	}

	@Test
	public void testFindByCommentsAndDescription() {
		HashMap<String, Integer> commentList = testData.getDescriptionTimes();
		String comment1 = commentList.keySet().iterator().next();

		List<Picture> pics = picService.findByCommentOrDescription(comment1, new Sort(Sort.Direction.DESC, "Id"));
		logger.info("Test find By Comment" + pics.get(0));
		assertEquals("Find comment like", 2, pics.size());
		pics = picService.findByCommentOrDescription("Leber d", new Sort(Sort.Direction.DESC, "Id"));
		assertEquals("multiple word query", 1, pics.size());
		pics = picService.findByCommentOrDescription("L x d L", new Sort(Sort.Direction.DESC, "Id"));
		assertTrue("checks if multiple search is working", pics.isEmpty());
	}

	@Test
	public void testSearchByTextCombinedTag() {
		List<Picture> testPic = picService.findByCommentOrDescription("Leber", new Sort(Sort.Direction.DESC, "Id"));
		List<Picture> foundPics = picService.searchByTextCombinedTag("leber", testPic.get(0).getTags(),
				new Sort(Sort.Direction.DESC, "Id"), "or");
		List<Tag> tags = tagService.findAll();
		assertEquals("search combine, 1 Result from text search or tags ", 1, foundPics.size());
		foundPics = picService.searchByTextCombinedTag("", testPic.get(0).getTags(),
				new Sort(Sort.Direction.DESC, "Id"), "and");
		assertEquals("search by tags, same in 2 Pictures, with and", 2, foundPics.size());
		foundPics = picService.searchByTextCombinedTag("", tags.subList(2, 4), new Sort(Sort.Direction.DESC, "Id"),
				"or");
		assertEquals("Search images with tags from all 3 with or", 3, foundPics.size());
		foundPics = picService.searchByTextCombinedTag("", tags.subList(2, 4), new Sort(Sort.Direction.DESC, "Id"),
				"and");
		assertEquals("Search imgaes with tags from all 3 with or, should find nothing", 0, foundPics.size());
		foundPics = picService.searchByTextCombinedTag("Pick Lunge", tags.subList(2, 4),
				new Sort(Sort.Direction.DESC, "Id"), "or");
		assertEquals("search imgaes by text with two words and tags from all with or", 2, foundPics.size());

	}

}

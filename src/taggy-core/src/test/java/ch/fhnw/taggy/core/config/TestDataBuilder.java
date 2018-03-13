package ch.fhnw.taggy.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;
/**
 * pic1 and pic2 has the same Tags the first Three of the list
 * pic3 has the other 3 Tags
 * @author 7977.667
 *
 */
@Transactional
public class TestDataBuilder {
	private PictureService picService;
	private TagsService tagService;
	private List<Picture> testPics;
	private HashMap<String, Integer> descriptionTimes;
	
	public TestDataBuilder(PictureService picService, TagsService tagService){
		this.picService = picService;
		this.tagService = tagService;
	}
	
	private void insertPicture(){
		descriptionTimes=new HashMap<>();
		descriptionTimes.put("unge", 1); descriptionTimes.put("eber", 1);
		Picture pic1 = new Picture( "Best Pick ever", 1101,"Herz in Diagnose");
		Picture pic2 = new Picture( "Good Pick good Leber", 1114,"Drinker Leber");
		Picture pic3 = new Picture( "Shower Pick Lunge", 112,"Raucher Lunge");
		testPics = new ArrayList<>();
		testPics.add(pic1);testPics.add(pic2);testPics.add(pic3);
		picService.save(pic1).save(pic2).save(pic3);
	}
	public HashMap<String, Integer> getDescriptionTimes() {
		return descriptionTimes;
	}

	private void insertTags(){
		Tag tag1 = new Tag("Schön");
		Tag tag2 = new Tag("Brutal");
		Tag tag3 = new Tag("komisch");
		Tag tag4 = new Tag("blutig");
		Tag tag5 = new Tag("grün");
		Tag tag6 = new Tag("rot");
		tagService.save(tag1).save(tag2).save(tag3).save(tag4).save(tag5).save(tag6);
		tagService.addTagToPicture(testPics.get(0).getId(), tag1.getTagName()).addTagToPicture(testPics.get(0).getId(), tag2.getTagName())
		.addTagToPicture(testPics.get(0).getId(), tag3.getTagName());
		tagService.addTagToPicture(testPics.get(1).getId(), tag1.getTagName()).addTagToPicture(testPics.get(1).getId(), tag2.getTagName())
		.addTagToPicture(testPics.get(1).getId(), tag3.getTagName());
		tagService.addTagToPicture(testPics.get(2).getId(), tag4.getTagName()).addTagToPicture(testPics.get(2).getId(), tag5.getTagName())
		.addTagToPicture(testPics.get(2).getId(), tag6.getTagName());
		
	}
	public void makeTestSituation(){
		insertPicture(); insertTags();
	}
	
	public List<Picture> getTestPics(){
		return testPics;
	}

}

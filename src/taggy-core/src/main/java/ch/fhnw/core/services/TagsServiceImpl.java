package ch.fhnw.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.PictureRepository;
import ch.fhnw.core.repository.TagsRepository;
@Transactional
@Service("tagsService")
public class TagsServiceImpl implements TagsService{
	@Autowired
	TagsRepository tagsRepository;
	
	@Autowired
	PictureRepository picRepository;

	@Override
	public Tag findByName(String tagname) {
		return tagsRepository.findByName(tagname);
	}

	@Override
	public TagsService save(Tag tag) {
		tagsRepository.save(tag);
		return this;
	}

	@Override
	public TagsService addTagToPicture(Integer pictureID, String tagName) {
		Tag t = tagsRepository.findByName(tagName);
		Picture pic = picRepository.findOne(pictureID);
		if(t==null){
			throw new RuntimeException("Tag not found");
		}
		if(t.getPictures().contains(pic)){
			return this;
		}else{
			
			t.addPicture(pic);
			tagsRepository.save(t);
			return this;
		}
		
	}

	@Override
	public Boolean deleteTagFromPicture(Integer pictureID, Integer tagID) {
		Tag tag = tagsRepository.findOne(tagID);
		Picture pic = picRepository.findOne(pictureID);
		if (tag== null){
			return false;
		}
		if(!tag.getPictures().contains(pic)){
			return false;
		}else{
			tag.removePicture(pic);
			tagsRepository.save(tag);
			return true;
		}
	}

	@Override
	public List<Tag> findByPicture(Picture picture) {
		return tagsRepository.findByPictures(picture);
	}

	@Override
	public void deleteTag(Tag tag) {
		tag.removePicsFromTag();
		tagsRepository.delete(tag);
	}

	@Override
	public void deleteTagIn(List<Tag> tags) {
		for (Tag tag : tags){
			tag.removePicsFromTag();
		}
		tagsRepository.delete(tags);
	}

}

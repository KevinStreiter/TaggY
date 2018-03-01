package ch.fhnw.core.services;

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
	public Tag save(Tag tag) {
		return tagsRepository.save(tag);
	}

	@Override
	public Tag addTagToPicture(Integer pictureID, String tagName) {
		Tag t = tagsRepository.findByName(tagName);
		Picture pic = picRepository.findOne(pictureID);
		if(t==null){
			throw new RuntimeException("Tag not found");
		}
		if(t.getPictures().contains(pic)){
			return null;
		}else{
			t.addPicture(pic);
			return tagsRepository.save(t);
		}
		
	}

	@Override
	public Boolean deleteTagFromBook(Integer pictureID, Integer tagID) {
		// TODO Auto-generated method stub
		return null;
	}

}

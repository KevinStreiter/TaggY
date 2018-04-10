package ch.fhnw.core.services;

import java.util.List;
import java.util.stream.Stream;

import ch.fhnw.core.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.PictureRepository;

@Transactional
@Service("tagsService")
public class TagsServiceImpl implements TagsService {

	@Autowired
	TagsRepository tagsRepository;

	@Autowired
	PictureRepository picRepository;

	@Override
	public TagsService addTagToPicture(Long pictureID, String tagName) {
		Tag t = tagsRepository.findByName(tagName);
		Picture pic = picRepository.findOne(pictureID);
		if (t == null) {
			throw new RuntimeException("Tag not found");
		}
		if (t.getPictures().contains(pic)) {
			return this;
		} else {

			t.addPicture(pic);
			tagsRepository.save(t);
			return this;
		}

	}

	@Override
	public Boolean deleteTagFromPicture(Long pictureID, Long tagID) {
		Tag tag = tagsRepository.findOne(tagID);
		Picture pic = picRepository.findOne(pictureID);
		if (tag == null) {
			return false;
		}
		if (!tag.getPictures().contains(pic)) {
			return false;
		} else {
			tag.removePicture(pic);
			tagsRepository.save(tag);
			return true;
		}
	}

	@Override
	public void deleteTag(Tag tag) {
		Stream<Picture> tempPictures = picRepository.findByTags(tag);
		if (tempPictures != null) {
			tag.removePicturesFromTag();
		}
		tagsRepository.delete(tag);
	}

	@Override
	public void deleteTagIn(List<Tag> tags) {
		for (Tag tag : tags) {
			tag.removePicturesFromTag();
		}
		tagsRepository.delete(tags);
	}

	@Override
	public List<Tag> findAll() {
		return tagsRepository.findAll();
	}

	@Override
	public List<Tag> findByPicture(Picture picture) {
		return tagsRepository.findByPictures(picture);
	}

	@Override
	public Tag findByName(String tagName) {
		return tagsRepository.findByName(tagName);
	}

	@Override
	public TagsService save(Tag tag) {
		tagsRepository.save(tag);
		return this;
	}
}

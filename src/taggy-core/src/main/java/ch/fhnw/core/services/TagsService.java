package ch.fhnw.core.services;

import java.util.List;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

public interface TagsService {

	Tag findByName(String tagName);

	List<Tag> findByPicture(Picture picture);

	TagsService save(Tag tag);

	TagsService addTagToPicture(Long pictureID, String tagName);

	Boolean deleteTagFromPicture(Long pictureID, Long tagID);

	void deleteTag(Tag tag);

	void deleteTagIn(List<Tag> tags);

	List<Tag> findAll();
}

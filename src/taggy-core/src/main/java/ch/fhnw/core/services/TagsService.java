package ch.fhnw.core.services;

import ch.fhnw.core.domain.Tag;

public interface TagsService {
	
	Tag findByName(String tagName);
	Tag save(Tag tag);
	public Tag addTagToPicture(Integer pictureID, String tagName);
	public Boolean deleteTagFromBook(Integer pictureID, Integer tagID);

}

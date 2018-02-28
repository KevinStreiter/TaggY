package ch.fhnw.core.services;

import ch.fhnw.core.domain.Tag;

public interface TagsService {
	
	Tag findByName(String tagname);
	Tag save(Tag tag);

}

package ch.fhnw.core.services;


import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

public interface PictureService {
	
	Stream<Picture> findByComment(String pictureName);
	Picture findById(Integer id);
	Stream<Picture> findByTag_id(Integer id);
	Stream<Picture> findPictureByTags(Set<String> tags);
	List<Picture> findPictureByTagsAnd(List<Tag> tags);
	void save(Picture pic);
	

}

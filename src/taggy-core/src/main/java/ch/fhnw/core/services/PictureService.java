package ch.fhnw.core.services;


import java.util.List;
import java.util.stream.Stream;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

public interface PictureService {
	
	Stream<Picture> findByComment(String pictureComment);
	Picture findById(Long id);
	Stream<Picture> findByTag_id(Long id);
	Stream<Picture> findPictureByTag(Tag tag);
	List<Picture> findPictureByTagsAnd(List<Tag> tags);
	Stream<Picture> findPictureByTagsOr(List<Tag> tags);
	PictureService save(Picture pic);
	List<Picture> findByCommentContaining(String partComment);
	List<Picture> findByDescriptionContaining(String partDescription);
	List<Picture> findAll();
}

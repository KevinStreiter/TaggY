package ch.fhnw.core.services;


import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

public interface PictureService {
	
	Picture findByComment(String pictureName);
	Picture findById(Integer id);
	void uptdatePicture(Picture pic);
	Stream<Picture> findPictureByTags(Set<String> tags);
	
	
	
	
	
	

}

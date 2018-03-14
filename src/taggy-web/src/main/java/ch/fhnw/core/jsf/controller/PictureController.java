package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.services.PictureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PictureService pictureService;

    private Picture picture = new Picture();
    private Set<Picture> pictures;

    public Picture getPicture() {
        return picture; }

    public Set<Picture> getPictures(){
    	if(pictures==null){
    		pictures = new HashSet<>(pictureService.findAll());
    	}
        return pictures;
    }
    public String getDescription(){
    	return picture.getDescription();
    }
    public String getComment(){
    	return picture.getComment();
    }
    public String textQuery(String query){
    	logger.info(query);
    	List<Picture> picturesList = pictureService.findByCommentContaining(query);
    	picturesList.addAll(pictureService.findByDescriptionContaining(query));
    	pictures = new HashSet<>(picturesList);
    	logger.info(""+pictures);
    	return "overview";
    }

    public String selectImage(Long id) {
    	if(id == null){
    		logger.info("given id: "+id);
    		return "overview";

    	}else{
    		picture = pictureService.findById(id);
    		logger.info("given id: "+id);
    		return "fullScreen";
    	}
    }
}



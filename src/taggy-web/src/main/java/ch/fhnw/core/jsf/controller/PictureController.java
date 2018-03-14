package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.services.PictureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PictureService pictureService;

    private Picture picture = new Picture();
    private List<Picture> pictures;
    
    


    public Picture getPicture() {
        return picture; }

    public List<Picture> getPictures(){
    	if(pictures==null){
    		pictures = pictureService.findAll(orderBy());
    	}
        return pictures;
    }
    public String getDescription(){
    	return picture.getDescription();
    }
    public String getComment(){
    	return picture.getComment();
    }
    public void setComment(String comment){
        picture.setComment(comment);
    }
    public String textQuery(String query){
    	logger.info(query);
    	pictures = pictureService.findByCommentOrDescription(query, orderBy());
    	logger.info(""+pictures);
    	return "overwiev";
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
        
    public String editComment(String comment){
        picture.setComment(comment);
        logger.info(picture.toString());
        pictureService.save(picture);
        return "fullScreen";
    }
    private Sort orderBy() {
        return new Sort(Sort.Direction.DESC, "Id");
    }
}



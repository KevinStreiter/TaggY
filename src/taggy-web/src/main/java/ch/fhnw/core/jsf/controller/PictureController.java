package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;

import javax.faces.context.FacesContext;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PictureService pictureService;
    private Picture picture = new Picture();
    private List<Picture> pictures;
    private List<Picture> selecetedPicture;
    private String selectedList="hello World";
    private String chose;
    
    public Picture getPicture() {
        return picture; }

    public List<Picture> getPictures(){
    	if(pictures==null){
    		logger.info("null");
    		pictures = pictureService.findAll(orderBy());
    		
    	}
    	logger.info(pictures.toString() + pictures.size());
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
    public void textQuery(String query){
    	logger.info(query+"\t"+chose);
    	pictures = pictureService.findByCommentOrDescription(query, orderBy());
    	logger.info(""+pictures);
    }
    public String resetSerach() {
    	pictures=null;
    	return "overview";
    }
    
    public String selectImage() {
    	String selected =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedPic");
    	Long id =Long.parseLong( selected);	
    	picture = pictureService.findById(id);
        logger.info("given id: "+id);
        return "fullScreen";
    }
    
    public void printOut() {
    	String value = FacesContext.getCurrentInstance().
    			getExternalContext().getRequestParameterMap().get("selectedPics");
    	logger.info(value);
    	logger.info(selectedList);
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

	public List<Picture> getSelecetedPicture() {
		return selecetedPicture;
	}

	public void setSelecetedPicture(List<Picture> selecetedPicture) {
		this.selecetedPicture = selecetedPicture;
	}

	public String getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}

	public String getChose() {
		return chose;
	}

	public void setChose(String chose) {
		this.chose = chose;
	}
   
}



package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.LongFunction;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PictureService pictureService;
    private Picture picture = new Picture();
    private List<Picture> pictures;
    private String selectedPicturesList;
    private String andOrChose ="or";
    private String textQuery="";
    private Set<Tag> searchTags= new HashSet<>();
    
    public Picture getPicture() {
        return picture; }

    public List<Picture> getPictures(){
    	if(pictures==null){
    		logger.info("getPictres is == null");
    		pictures = pictureService.findAll(orderBy());
    		
    	}
    	logger.info("getPictures "+pictures.toString() + "Number of Picutres" + pictures.size());
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
    private void pictureQuery() {
    	List<Tag> tags = new ArrayList<>();
    	tags.addAll(searchTags);
    	pictures=pictureService.searchByTextCombinedTag(textQuery, tags, orderBy(), andOrChose);
    	
    }
    public void changeChose(ValueChangeEvent e) {
    	andOrChose=e.getNewValue().toString();
    }
    
    public void textQuery(String query){
    	textQuery=query;
    	pictureQuery();
    	logger.info("TextQuerry "+ query);
    }
    public void onRowSelect(SelectEvent event) {
        searchTags.add(((Tag) event.getObject()));
        pictureQuery();
        logger.info("row Select "+((Tag) event.getObject()).getTagName());
    }
   
    public void onRowUnselect(UnselectEvent event) {
    	searchTags.remove(((Tag) event.getObject()));
    	pictureQuery();
        logger.info("row Unselect "+((Tag) event.getObject()).getTagName());
    }
    private Sort orderBy() {
        return new Sort(Sort.Direction.DESC, "Id");
    }
    public String resetSearch() {
    	pictures=null;
    	return "overview";
    }
    
    public String selectImage() {
    	String selected =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedPic");
    	Long id =Long.parseLong( selected);	
    	picture = pictureService.findById(id);
        logger.info("Seleceted Image given id: "+id);
        return "fullScreen";
    }
    
    public void printOut() {
    	String value = FacesContext.getCurrentInstance().
    			getExternalContext().getRequestParameterMap().get("selectedPics");
    	logger.info("printOut" + value);
    	logger.info(selectedPicturesList);
    }
        
    public String editComment(String comment){
        picture.setComment(comment);
        pictureService.save(picture);
        return "fullScreen";
    }
   

	public String getSelectedList() {
		return selectedPicturesList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedPicturesList = selectedList;
	}

	public String getChose() {
		return andOrChose;
	}

	public void setChose(String chose) {
		this.andOrChose = chose;
	}
   
}



package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;

import ch.fhnw.core.services.TagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PictureService pictureService;

    @Autowired
    TagsService tagsService;

    private Picture picture = new Picture();
    private List<Picture> pictures;
    private List<Picture> selectedPicture;
    private List<Tag> tags;
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
    	pictures = null;
    	picture = null;
    	return "overview";
    }

    public String selectImage() {
    	String selected =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedPic");
    	Long id = Long.parseLong( selected);
    	picture = pictureService.findById(id);
        logger.info("given id: "+id);
        tags = picture.getTags();
        return "fullScreen";
    }
    
    public void printOut() {
    	String value = FacesContext.getCurrentInstance().
    			getExternalContext().getRequestParameterMap().get("selectedPics");
    	logger.info(value);
    	logger.info(selectedList);
        logger.info(FacesContext.getCurrentInstance().getExternalContext().toString());
    }
        
    public void editComment(String comment){
        picture.setComment(comment);
        logger.info(picture.toString());
        pictureService.save(picture);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Info", "Comment has been saved"));
    }

    private Sort orderBy() {
        return new Sort(Sort.Direction.DESC, "Id");
    }

	public List<Picture> getSelectedPicture() {
		return selectedPicture;
	}

	public void setSelectedPicture(List<Picture> selectedPicture) {
		this.selectedPicture = selectedPicture;
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


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void deleteTagOnPicture(Long tagId){
        logger.info(tagId.toString());
        tagsService.deleteTagFromPicture(picture.getId(),tagId);
        picture = pictureService.findById(picture.getId());
        tags = picture.getTags();
    }

    public void addTagToPictures(String tagName){
        if(picture == null) {
            String selectedPicturesIds = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("selectedPics");
            if (selectedPicturesIds.length() != 0) {
                String[] pictureIds = selectedPicturesIds.split(",");
                logger.info("SelectedPics:" + selectedPicturesIds);

                for (String pictureId : pictureIds) {
                    logger.info(pictureId);
                    tagsService.addTagToPicture(Long.parseLong(pictureId), tagName);
                    logger.info(tagsService.findByName(tagName).getPictures().toString());
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Info", "Image-Id(s): " +
                        selectedPicturesIds + " saved to Tag: " + tagName));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Info", "No Images selected"));
            }
        } else {
            Long selectedPictureId = picture.getId();
            tagsService.addTagToPicture(selectedPictureId, tagName);
            picture = pictureService.findById(picture.getId());
            tags = picture.getTags();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Info", "Image-Id: " +
                     picture.getId() + " saved to Tag: " + tagName));
        }

    }
}



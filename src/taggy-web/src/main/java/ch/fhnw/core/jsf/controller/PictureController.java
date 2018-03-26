package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;

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
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PictureService pictureService;
	@Autowired
	TagsService tagService;
	private Picture picture = new Picture();
	private List<Picture> pictures;
	private String selectedPicturesList;
	private String andOrChose = "or";
	private List<Tag> selectedTags = new ArrayList<>();
	private String searchText = "";

	private void pictureQuery() {
		pictures = pictureService.searchByTextCombinedTag(searchText, selectedTags, orderBy(), andOrChose);

	}
	private Sort orderBy() {
		return new Sort(Sort.Direction.DESC, "Id");
	}
    	logger.info(pictures.toString() + pictures.size());
    	picture = null;
        return pictures;
}

	public void changeChose(ValueChangeEvent e) {
		andOrChose = e.getNewValue().toString();
	}

	public void textQuery() {
		pictureQuery();
	}
	
	public void toggleAll() {
		pictureQuery();
		logger.info("Toggle All " +selectedTags);
	}

	public void onRowSelect(SelectEvent event) {
		pictureQuery();
		logger.info("row Select " + ((Tag) event.getObject()).getTagName() + " " + getSelectedTags().toString());
	}

	public void onRowUnselect(UnselectEvent event) {
		pictureQuery();
		logger.info("row Unselect " + ((Tag) event.getObject()).getTagName());
	}

	public String resetSearch() {
		pictures = null;
		searchText="";
		selectedTags = new ArrayList<>();
		return "overview?faces-redirect=true";
	}

	public String selectImage() {
		String selected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("selectedPic");
    	Long id = Long.parseLong( selected);
		picture = pictureService.findById(id);
		logger.info("Seleceted Image given id: " + id);
		tags = picture.getTags();
		return "fullScreen?faces-redirect=true";
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
		pictureService.save(picture);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Info", "Comment has been saved"));
		return "fullScreen?faces-redirect=true";
	}
}

	public void setComment(String comment) {
		picture.setComment(comment);
	}

	public List<Picture> getSelectedPicture() {
		return selectedPicture;
	public String getComment() {
		return picture.getComment();
	}

	public void setSelectedPicture(List<Picture> selectedPicture) {
		this.selectedPicture = selectedPicture;
	public Picture getPicture() {
		return picture;
	}

	public List<Picture> getPictures() {
		if (pictures == null) {
			logger.info("getPictres is == null");
			pictures = pictureService.findAll(orderBy());
		}
		logger.info("getPictures Number of Picutres: " + pictures.size() +"Ausgewählte Bilder: " + pictures.toString() );
		return pictures;
	}

	public String getDescription() {
		return picture.getDescription();
	}

	public void setSelectedList(String selectedList) {
		this.selectedPicturesList = selectedList;
	}

	public String getSelectedList() {
		return selectedPicturesList;
	}

	public String getChose() {
		return andOrChose;
	}

	public void setChose(String chose) {
		this.andOrChose = chose;
	}


    public List<Tag> getTags() {
        return tags;
	public List<Tag> getSelectedTags() {
		return selectedTags;
	}

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
	public void setSelectedTags(List<Tag> selectedTags) {
		this.selectedTags = selectedTags;
	}

    public void deleteTagOnPicture(Long tagId){
        logger.info("Deleted Image Tag: " + tagId.toString());
        tagsService.deleteTagFromPicture(picture.getId(),tagId);
        picture = pictureService.findById(picture.getId());
        tags = picture.getTags();
    }

    public void addTagToPictures(String tagName){
        logger.info(tagName);
        if(picture == null) {
            String selectedPicturesIds = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("selectedPics");
            if (selectedPicturesIds.length() != 0) {
                String[] pictureIds = selectedPicturesIds.split(",");
                logger.info("SelectedPics: " + selectedPicturesIds);

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
            tagsService.addTagToPicture(selectedPictureId,tagName);
            tags = tagsService.findByPicture(picture);
            picture = pictureService.findById(selectedPictureId);
            logger.info("Image Tags: "+tagsService.findByPicture(picture).toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Info", "Image-Id: " +
                     picture.getId() + " saved to Tag: " + tagName));
        }

    }
}


	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}

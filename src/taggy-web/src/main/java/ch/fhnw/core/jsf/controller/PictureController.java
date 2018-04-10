package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.PictureService;
import ch.fhnw.core.services.TagsService;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

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
	private Picture picture =null;
	private List<Picture> pictures;
	private String selectedPicturesList;
	private String andOrChose = "or";
	private List<Tag> selectedTags = new ArrayList<>();
	private String searchText = "";
	private List<Tag> tags;

	private void pictureQuery() {
		pictures = pictureService.searchByTextCombinedTag(searchText, selectedTags, orderBy(), andOrChose);
		logger.info("Picture Querry: "+pictures.size());

	}
	private Sort orderBy() {
		return new Sort(Sort.Direction.DESC, "Id");
	}


	public void changeChose(ValueChangeEvent e) {
		andOrChose = e.getNewValue().toString();
		logger.info("RadioButton Changesed, value: "+andOrChose);
		pictureQuery();
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
		logger.info("row Select " + ((Tag) event.getObject()).getTagName());
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
		tags = tagService.findByPicture(picture);
		logger.info("Seleceted Image given id: "+picture.toString());
		
		return "fullScreen?faces-redirect=true";
	}

        
    public void editComment(String comment){
		picture.setComment(comment);
		pictureService.save(picture);
		pictures = pictureService.findAll(orderBy());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Info", "Comment has been saved"));
	}


	public void setComment(String comment) {
		picture.setComment(comment);
	}

	public String getComment() {
		return picture.getComment();
	}

	public Picture getPicture() {
		return picture;
	}

	public List<Picture> getPictures() {
		if (pictures == null) {
			logger.info("getPictures is == null");
			pictures = pictureService.findAll(orderBy());
		}
		logger.info("getPictures Number of Picutres: " + pictures.size() +"found Pictures: " + pictures.toString() );
		picture=null;
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


    public List<Tag> getSelectedTags() {
		return selectedTags;
	}

    public void setSelectedTags(List<Tag> selectedTags) {
		this.selectedTags = selectedTags;
	}
	public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
	public List<Tag> getTags() {
	    return tags;
	}
	public void deleteTagOnPicture(Long tagId){
        logger.info("Deleted Image Tag: " + tagId.toString());
        tagService.deleteTagFromPicture(picture.getId(),tagId);
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
                    tagService.addTagToPicture(Long.parseLong(pictureId), tagName);
                    logger.info(tagService.findByName(tagName).getPictures().toString());
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Info", "Image-Id(s): " +
                        selectedPicturesIds + " has/have been tagged with  " + tagName));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Info", "No Images selected"));
            }
        } else {
            Long selectedPictureId = picture.getId();
            tagService.addTagToPicture(selectedPictureId,tagName);
            tags = tagService.findByPicture(picture);
            picture = pictureService.findById(selectedPictureId);
            logger.info("Image Tags: "+tagService.findByPicture(picture).toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Info", "Displayed Image has been tagged with " + tagName));
        }

    }

    public void onReload() {
        logger.info("onReload:");
        pictureQuery();
    }
   

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}

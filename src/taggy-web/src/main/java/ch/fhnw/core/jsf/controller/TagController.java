package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.TagsService;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Scope(value = "session")
@Component(value = "tagController")
public class TagController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TagsService tagsService;

    private Tag selectedTag;
    private List<Tag> selectedTags;
    

    private Tag tag = new Tag();

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<Tag> getTags(){
        return tagsService.findAll();
    }

    public String edit(String tagName){
        if(tagName.isEmpty()){
            tag = new Tag();
        } else {
            tag = tagsService.findByName(tagName);
        }
        return "overview";
    }

    public String save(){
        Tag tagTemp = new Tag (tag.getTagName());
        List<Tag> tags = getTags();
        for(Tag tag : tags){
            if((tag.getTagName().equals(tagTemp.getTagName()))){
                return "overview";
            }
        }
        tagsService.save(tagTemp);
        return "overview";
    }

    
    public void deleteSelectedTag(String name){
        Tag tempTag = tagsService.findByName(name);
        logger.info(tempTag.toString());
        if(tempTag != null) {
            
            tagsService.deleteTag(tempTag);
        }
    }
    
    

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
    }
    
    public List<Tag> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<Tag> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public void setService(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    public void buttonAction(ActionEvent actionEvent) {
        logger.info("Welcome to Primefaces!!");
    }
}


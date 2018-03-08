package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.TagsService;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Scope(value = "session")
@Component(value = "tagController")
public class TagController {

    @Autowired
    private TagsService tagsService;

    private Tag tag = new Tag();

    public Tag getTag() {
        return tag; }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<Tag> getTags(){
        return tagsService.findAll();
    }

    public String edit(String tagName){
        if(tagName.isEmpty()){
            tag = new Tag();
        }
        else {
            tag = tagsService.findByName(tagName);
        }
        return "listTags";
    }

    public String save(){
        Tag tagTemp = new Tag (tag.getTagName());
        List<Tag> tags = getTags();
        for(Tag tag : tags){
            if((tag.getTagName().equals(tagTemp.getTagName()))){
                return "listTags";
            }
        }
        tagsService.save(tagTemp);
        return "listTags";
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Tag Selected", ((Tag) event.getObject()).getTagName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Tag Unselected", ((Tag) event.getObject()).getTagName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}


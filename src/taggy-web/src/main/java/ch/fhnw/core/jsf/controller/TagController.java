package ch.fhnw.core.jsf.controller;




import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        return "editTag";
    }

    public String save(){
        tagsService.save(tag);
        return "listTags";
    }
}


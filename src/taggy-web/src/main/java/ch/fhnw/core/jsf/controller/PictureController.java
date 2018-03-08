package ch.fhnw.core.jsf.controller;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope(value = "session")
@Component(value = "pictureController")
public class PictureController {

    @Autowired
    PictureService pictureService;

    private Picture picture = new Picture();

    public Picture getPicture() {
        return picture; }

    public List<Picture> getPictures(){
        return pictureService.findAll();
    }

    public String selectImage() {
        return "fullScreen";
    }
}



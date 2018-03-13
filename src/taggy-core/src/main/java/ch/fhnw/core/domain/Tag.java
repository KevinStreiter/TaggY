package ch.fhnw.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name = "tags")
public class Tag {
	

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToMany(mappedBy ="tags")
	private Set<Picture> pictures = new HashSet<Picture>();
	
	public Tag(){}
	
	public Tag(String TagName){
		this.name=TagName;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", TagName=" + name + ", pictures=" + pictures + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return name;
	}

	public void setTagName(String tagName) {
		name = tagName;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
	
	public void addPicture(Picture picture){
		if(!getPictures().contains(picture)){
			getPictures().add(picture);
		}
		if (!picture.getTags().contains(this)){
			picture.getTags().add(this);
		}
	}
	public void removePicture(Picture picture){
		if (getPictures().contains(picture)){
			getPictures().add(picture);
		}
		if (picture.getTags().contains(this)){
			picture.getTags().remove(this);
		}
	}
	
	public void removePicsFromTag(){
		for (Picture pic : pictures){
			pic.removeTag(this);
		}
	}
	

}

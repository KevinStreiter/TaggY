package ch.fhnw.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture {
	
	@Id
	private Integer id;
	
	@Column(nullable=false)
	private String uri;
	
	@Column(nullable=true)
	private String comment;
	
	@ManyToMany(targetEntity = Tag.class, cascade =CascadeType.ALL)
	@JoinTable(name = "pictures_tags", joinColumns = {@JoinColumn(name = "pictue_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
	@OrderBy("name ASC")
	private List<Tag> tags = new ArrayList<>();
	
	public Picture(){}
	
	public Picture(String uri, String comment, Integer id){
		this.uri=uri;
		this.comment=comment;
		this.id=id;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", uri=" + uri + ", comment=" + comment +"]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public void addTag(Tag tag){
		if (!getTags().contains(tag)){
			getTags().add(tag);
		}
		if (!tag.getPictures().contains(this)){
			tag.getPictures().add(this);
		}
	}
	public void removeTag(Tag tag){
		if(getTags().contains(tag)){
			getTags().remove(tag);
		}
		if(tag.getPictures().contains(tag)){
			tag.getPictures().remove(tag);
		}
	}
	

}

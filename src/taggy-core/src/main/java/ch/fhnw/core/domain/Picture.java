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
	private Long id;
	
	@Column(nullable=false)
	private String uid;
	
	@Column(nullable=true)
	private String comment;
	
	@Column(nullable=true)
	private String description;
	
	@ManyToMany(targetEntity = Tag.class, cascade =CascadeType.ALL)
	@JoinTable(name = "pictures_tags", joinColumns = {@JoinColumn(name = "pictue_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
	@OrderBy("name ASC")
	private List<Tag> tags = new ArrayList<>();
	
	public Picture(){}
	
	public Picture(String comment, Long id, String description, String uid){
		this.uid = uid;
		this.comment=comment;
		this.id=id;
		this.description=description;
	}
	
	public Picture(String comment, Long id, String description){
		this.comment=comment;
		this.id=id;
		this.description=description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", comment=" + comment +", description="+description+"]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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
		if(tag.getPictures().contains(this)){
			tag.getPictures().remove(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Picture other = (Picture) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

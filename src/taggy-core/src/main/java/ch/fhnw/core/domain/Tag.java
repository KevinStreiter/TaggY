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

	@ManyToMany(mappedBy = "tags")
	private Set<Picture> pictures = new HashSet<Picture>();

	public Tag() {
	}

	public Tag(String TagName) {
		this.name = TagName;
	}

	public void addPicture(Picture picture) {
		if (!getPictures().contains(picture)) {
			getPictures().add(picture);
		}
		if (!picture.getTags().contains(this)) {
			picture.getTags().add(this);
		}
	}

	public void removePicture(Picture picture) {
		if (getPictures().contains(picture)) {
			getPictures().add(picture);
		}
		if (picture.getTags().contains(this)) {
			picture.getTags().remove(this);
		}
	}

	public void removePicturesFromTag() {
		for (Picture pic : pictures) {
			pic.removeTag(this);
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
		Tag other = (Tag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", TagName=" + name + ", pictures=" + pictures + "]";
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public String getTagName() {
		return name;
	}

	public void setTagName(String tagName) {
		name = tagName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

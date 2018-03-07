package ch.fhnw.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

@Repository
@Transactional
public interface TagsRepository extends JpaRepository<Tag, Integer>{

	Tag findByName(String tagName);
	List<Tag> findByPictures(Picture picture);

}

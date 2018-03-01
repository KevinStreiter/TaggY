package ch.fhnw.core.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Picture, Integer>{

	Picture findByComment(String pictureComment);
	List<Picture> findByTags(Tag tag);
	List<Picture> findByTagsIn(List<Tag>Tags); //Or solution
	List<Picture> deleteByIdIn(List<Integer> ids);
	
	List<Picture> findByIdIn(List<Integer> ids);
	List<Picture> findByIdInAndTags(List<Integer> ids,Tag tag);
	
	Picture findById(Integer id);
}

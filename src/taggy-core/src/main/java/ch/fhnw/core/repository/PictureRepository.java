package ch.fhnw.core.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Picture, Long>{
	
	
  	Stream<Picture> findByComment(String pictureComment);
	Stream<Picture> findByTags(Tag tag);
	Stream<Picture> findByTagsIn(List<Tag>Tags); //Or solution
	Stream<Picture> deleteByIdIn(List<Long> ids);
	Stream<Picture> findByIdIn(List<Long> ids);
	Stream<Picture> findByIdInAndTags(List<Long> ids,Tag tag);
	Picture findById(Long id);
	Stream<Picture> findByTags_id(Long id);
	List<Picture> findByCommentContaining(String partComment);
	List<Picture> findByDescriptionContaining(String partDescription);

}

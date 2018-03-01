package ch.fhnw.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.core.domain.Tag;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer>{

	Tag findByName(String tagName);
}

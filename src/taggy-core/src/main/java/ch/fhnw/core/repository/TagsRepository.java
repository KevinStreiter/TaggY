package ch.fhnw.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import ch.fhnw.core.domain.Tag;

public interface TagsRepository extends JpaRepository<Tag, Integer>{

	Tag findByName(String tagname);
}

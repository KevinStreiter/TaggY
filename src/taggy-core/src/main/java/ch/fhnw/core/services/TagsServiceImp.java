package ch.fhnw.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.TagsRepository;
@Transactional
@Service("tagsService")
public class TagsServiceImp implements TagsService{
	@Autowired
	TagsRepository tagsRepository;

	@Override
	public Tag findByName(String tagname) {
		return tagsRepository.findByName(tagname);
	}

	@Override
	public Tag save(Tag tag) {
		return tagsRepository.save(tag);
	}

}

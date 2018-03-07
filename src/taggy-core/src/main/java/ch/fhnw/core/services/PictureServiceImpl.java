package ch.fhnw.core.services;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.fhnw.core.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

@Transactional
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
	@Autowired
	PictureRepository picRepository;

	@Override
	public Stream<Picture> findByComment(String pictureComment) {
		return picRepository.findByComment(pictureComment);
	}

	@Override
	public Picture findById(Integer id) {
		return picRepository.findById(id);
	}


	@Override
	public List<Picture> findPictureByTagsAnd(List<Tag> tags) {
		List<Picture> picList=new ArrayList<>();
		if(tags.size()==0){
			return picList;
		}else if(tags.size()==1){
			return picRepository.findByTagsIn(tags).collect(Collectors.toList());
		}
		picList=picRepository.findByTags(tags.get(0)).collect(Collectors.toList());
		for(int i =1; i< tags.size() ;i++){
			List<Integer> ids = new ArrayList<>();
			for (Picture pic : picList){
				ids.add(pic.getId());
			}
			picList=picRepository.findByIdInAndTags(ids, tags.get(i)).collect(Collectors.toList());
		}
		return picList;
		
	}

	@Override
	public PictureService save(Picture pic) {
		picRepository.save(pic);
		return this;
		
	}

	@Override
	public Stream<Picture> findByTag_id(Integer id) {
		return picRepository.findByTags_id(id);
	}

	@Override
	public Stream<Picture> findPictureByTag(Tag tag) {
		return picRepository.findByTags(tag);
	}

	@Override
	public Stream<Picture> findPictureByTagsOr(List<Tag> tags) {
		return picRepository.findByTagsIn(tags);
	}

	

}

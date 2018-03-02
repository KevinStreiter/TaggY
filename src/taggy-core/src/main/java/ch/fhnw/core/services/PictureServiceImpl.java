package ch.fhnw.core.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.PictureRepository;
@Transactional
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
	@Autowired
	PictureRepository pictRepository;

	@Override
	public Stream<Picture> findByComment(String pictureComment) {
		return pictRepository.findByComment(pictureComment);
	}

	@Override
	public Picture findById(Integer id) {
		return pictRepository.findById(id);
	}


	@Override
	public Stream<Picture> findPictureByTags(Set<String> tags) {
		return findPictureByTags(tags);
	}

	@Override
	public List<Picture> findPictureByTagsAnd(List<Tag> tags) {
		List<Picture> picList=new ArrayList<>();
		if(tags.size()==0){
			return null;
		}else if(tags.size()==1){
			return pictRepository.findByTagsIn(tags).collect(Collectors.toList());
		}
		picList=pictRepository.findByTags(tags.get(0)).collect(Collectors.toList());
		for(int i =1; i< tags.size() ;i++){
			List<Integer> ids = new ArrayList<>();
			for (Picture pic : picList){
				ids.add(pic.getId());
			}
			picList=pictRepository.findByIdInAndTags(ids, tags.get(i)).collect(Collectors.toList());
		}
		return picList;
		
	}

	@Override
	public void save(Picture pic) {
		pictRepository.save(pic);
		
	}

	@Override
	public Stream<Picture> findByTag_id(Integer id) {
		return pictRepository.findByTags_id(id);
	}

	

}

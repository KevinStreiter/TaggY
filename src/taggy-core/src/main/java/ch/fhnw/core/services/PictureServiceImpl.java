package ch.fhnw.core.services;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.fhnw.core.repository.PictureRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;

@Transactional
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
	@Autowired
	PictureRepository picRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Stream<Picture> findByComment(String pictureComment) {
		return picRepository.findByComment(pictureComment);
	}

	@Override
	public Picture findById(Long id) {
		return picRepository.findById(id);
	}


	@Override
	public List<Picture> findPictureByTagsAnd(List<Tag> tags) {
		List<Picture> picList=new ArrayList<>();
		if(tags.size()==0){
			return picList;
		}else if(tags.size()==1){
			picList.addAll(picRepository.findByTagsIn(tags));
			return picList;
		}
		picList=picRepository.findByTags(tags.get(0)).collect(Collectors.toList());
		for(int i =1; i< tags.size() ;i++){
			List<Long> ids = new ArrayList<>();
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
	public List<Picture> findAll() {
		return picRepository.findAll();
	}

	@Override
	public Stream<Picture> findByTag_id(Long id) {
		return picRepository.findByTags_id(id);
	}

	@Override
	public Stream<Picture> findPictureByTag(Tag tag) {
		return picRepository.findByTags(tag);
	}

	@Override
	public List<Picture> findPictureByTagsOr(List<Tag> tags) {
		List<Picture> pictureList = new ArrayList<>();
		pictureList.addAll(picRepository.findByTagsIn(tags));
		return pictureList;
	}

	@Override
	public List<Picture> findByCommentContaining(String partComment) {
		return picRepository.findByCommentContaining(partComment);
	}

	@Override
	public List<Picture> findByDescriptionContaining(String partDescription) {
		return picRepository.findByDescriptionContaining(partDescription);
	}

	@Override
	public List<Picture> findAll(Sort sort) {
		return picRepository.findAll(sort);
	}

	@Override
	public List<Picture> findByCommentOrDescription(String query, Sort sort) {
		String[] queryParts = query.split(" ");
		logger.info("Length of splited query: "+queryParts.length+" "+queryParts.toString());
		List<Picture> pictures = null;
		for (String part:queryParts) {
			List<Picture> picturesTmp = picRepository.findByCommentContainingOrDescriptionContaining(part, part, sort);
			if(pictures==null) {
				pictures=picturesTmp;
			}else {
				List<Picture> result=new ArrayList<>();
				logger.info("Comparation: "+picturesTmp.toString()+" has list "+pictures.toString());
				for (Picture pic :picturesTmp) {
					if (pictures.contains(pic)) {
					result.add(pic);
					}
				}
				pictures=result;
				logger.info("Query Text-Search: "+pictures.toString()+query);
			}
		}
		return pictures;
	}

	@Override
	public List<Picture> searchByTextCombinedTag(String textQuery, List<Tag> tags, Sort sort, String andOr) {
		Set<Picture> result= new HashSet<>();
		List<Picture> endList= new ArrayList<>();
		if(tags.size()==0) {
			return findByCommentOrDescription(textQuery, sort);
		}
		logger.info("Search by Tag " + textQuery.split(" ").length);
		if(textQuery.length()==0) {
			if (andOr.equals("or")) {
				result.addAll(findPictureByTagsOr(tags)); 
				endList.addAll(result);
				return endList;				
			}else {
				return findPictureByTagsAnd(tags);
			}
		}
		
		if (andOr.equals("or")) {
			result.addAll(findPictureByTagsOr(tags));
			result.addAll(findByCommentOrDescription(textQuery, sort));
			endList.addAll(result);
			
			return endList;
		}else {
			result.addAll(findByCommentOrDescription(textQuery, sort));
			result.addAll(findPictureByTagsAnd(tags));
			endList.addAll(result);
			return endList;
		}
	}


}

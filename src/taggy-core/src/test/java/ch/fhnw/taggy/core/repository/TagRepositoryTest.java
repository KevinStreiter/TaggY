package ch.fhnw.taggy.core.repository;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import ch.fhnw.core.App;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.TagsRepository;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = App.class)
public class TagRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	TagsRepository tagsRepository;
	
	@Test
	public void testSave(){
		Tag tag = new Tag("Weiblich");
		assertNull("epmty dp",tag.getId());
		tag = tagsRepository.save(tag);
		System.out.println(tag.getTagName()+tag.getId());
		assertNotNull("generatet pk in dp", tag.getId());
	}

}

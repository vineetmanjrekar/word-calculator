package uk.co.floow.calculator.service;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;

import uk.co.floow.calculator.dao.FileDao;
import uk.co.floow.calculator.dao.WordCountDao;
import uk.co.floow.calculator.domain.DocumentWordCount;
import uk.co.floow.calculator.domain.FileDocument;
import uk.co.floow.calculator.domain.WordCount;

public class WordCalculatorServiceTest
{
	public static final String FILES_DB = "files";
	private MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), FILES_DB));
	private FileDao fileDao = new FileDao(mongoTemplate);
	private WordCountDao wordCountDao = new WordCountDao(mongoTemplate);
	private WordCalculatorService wordCalculatorService = new WordCalculatorService(fileDao, wordCountDao);


	@Before
	public void setup()
	{
		mongoTemplate.remove(new Query(), FileDocument.class);
		mongoTemplate.remove(new Query(), DocumentWordCount.class);
	}

	@Test
	public void testShouldCountTheNumberOfWordsFromCollection() {
		//given:
		final FileDocument objectToSave = new FileDocument();
		final String fileId = "fileId1";
		objectToSave.setFileId(fileId);
		objectToSave.setLines(Lists.newArrayList("it", "is", "a", "sentence"));
		fileDao.save(objectToSave);

		//when
		wordCalculatorService.calculateWordCount(fileId);

		//then:
		final Set<Set<WordCount>> wordCounts = wordCountDao.findBy(fileId);
		assertEquals(4, wordCounts.stream().flatMap(Set::stream).collect(Collectors.toSet()).size());


	}

}
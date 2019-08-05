package uk.co.floow.calculator.dao;

import java.util.List;

import org.assertj.core.util.Streams;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;

import uk.co.floow.calculator.domain.FileDocument;
import uk.co.floow.calculator.domain.WordCount;


public class FileDaoTest
{
	public static final String FILES_DB = "files";
	private MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), FILES_DB));
	private FileDao fileDao = new FileDao(mongoTemplate);


	@Before
	public void setup()
	{
		mongoTemplate.remove(FileDocument.class);
		mongoTemplate.remove(WordCount.class);
	}

	@Test
	public void testShouldCheckThatCorrectIndexesAreSetupOnTheCollection()
	{
		//given:
		final List<IndexInfo> indexInfo = mongoTemplate.indexOps(FileDocument.class).getIndexInfo();
		//when:
		//then:
		Assert.assertEquals(1, indexInfo.size());
	}

	@Test
	public void testShouldCheckIfMongoCollectionExistsAndReturnsResults()
	{

		//given:
		final FileDocument objectToSave = new FileDocument();
		objectToSave.setFileId("fileId1");
		objectToSave.setLines(Lists.newArrayList("it", "is", "a", "sentence"));
		//when:
		fileDao.save(objectToSave);

		//then:
		final List<FileDocument> fileDocuments = mongoTemplate.find(Query.query(Criteria.where("fileId").is("fileId1")), FileDocument.class);
		Assert.assertEquals(1, fileDocuments.size());
	}

	@Test
	public void testShouldCountNumberOfWordsInAMultipleLineDocument()
	{

		//given:
		final FileDocument objectToSave = new FileDocument();
		objectToSave.setFileId("fileId1");
		objectToSave.setLines(Lists.newArrayList("it is ", "is a is ", "a sentence", "sentence it not"));

		fileDao.save(objectToSave);

		//when:
		final MapReduceResults<WordCount> wordCounts = fileDao.mapReduce();

		//then:
		Assert.assertEquals(2, getWordCountFor(wordCounts, "sentence"));
		Assert.assertEquals(1, getWordCountFor(wordCounts, "not"));
		Assert.assertEquals(3, getWordCountFor(wordCounts, "is"));

	}

	private long getWordCountFor(final MapReduceResults<WordCount> wordCounts, String word)
	{
		return Streams.stream(wordCounts)
				.filter(wordCount -> wordCount.getId().equals(word))
				.findFirst()
				.get().getValue();
	}
}

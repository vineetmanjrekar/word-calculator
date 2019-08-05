package uk.co.floow.calculator.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;

import uk.co.floow.calculator.domain.FileDocument;


public class FileDaoTest
{
	public static final String FILES_DB = "files";
	private MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), FILES_DB));
	private FileDao fileDao = new FileDao(mongoTemplate);


	@Before
	public void setup()
	{
		mongoTemplate.remove(FileDocument.class);
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
}

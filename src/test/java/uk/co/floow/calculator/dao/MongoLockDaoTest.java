package uk.co.floow.calculator.dao;

import static uk.co.floow.calculator.dao.FileDaoTest.FILES_DB;

import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.MongoClient;

import uk.co.floow.calculator.domain.MongoLock;

public class MongoLockDaoTest
{

	private MongoLockDao mongoLockDao = new MongoLockDao();
	private MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), FILES_DB));


	@Before
	public void setup()
	{
		mongoTemplate.dropCollection(MongoLock.class);
	}

	@Test
	public void testLockAndUnlockOfCollection()
	{
		//given
		mongoLockDao.save("fileId1", "chunkId1");
		mongoLockDao.save("fileId1", "chunkId2");
		mongoLockDao.save("fileId1", "chunkId3");
		mongoLockDao.save("fileId1", "chunkId4");

		//when
		mongoLockDao.setCompleteStatus("fileId1", "chunkId1");

		//then
		final MongoLock mongoLock = mongoTemplate.findOne(Query.query(Criteria.where("_id").is("fileId1")), MongoLock.class);
		Assert.assertEquals(3,
				mongoLock.getChunks().stream().filter(chunkStatus -> chunkStatus.isStatus()).collect(Collectors.toSet()).size());
	}


}
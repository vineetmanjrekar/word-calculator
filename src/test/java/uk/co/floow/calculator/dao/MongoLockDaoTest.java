package uk.co.floow.calculator.dao;

import static uk.co.floow.calculator.dao.FileDaoTest.FILES_DB;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
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
		mongoTemplate.remove(new Query(), MongoLock.class);
	}

	@Test
	public void testLockAndUnlockOfCollection()
	{

		mongoLockDao.save("fileId", "chunkId");

	}


}
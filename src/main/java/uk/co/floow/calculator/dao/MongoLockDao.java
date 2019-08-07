package uk.co.floow.calculator.dao;

import java.util.Date;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import uk.co.floow.calculator.domain.MongoLock;

@Component
public class MongoLockDao extends MongoTemplateDao
{

	public void save(String fileId, String chunkId)
	{
		getMongoTemplate().findAndModify(Query.query(Criteria.where("_id").is(fileId)),
				new Update().push("chunkId", chunkId).set("dateCreated", new Date()),
				FindAndModifyOptions.options().upsert(true), MongoLock.class);

	}


}

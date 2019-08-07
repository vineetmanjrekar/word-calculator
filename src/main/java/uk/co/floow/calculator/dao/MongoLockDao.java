package uk.co.floow.calculator.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import uk.co.floow.calculator.domain.ChunkStatus;
import uk.co.floow.calculator.domain.MongoLock;

@Component
public class MongoLockDao extends MongoTemplateDao
{

	public static final String ID = "_id";

	public void save(String fileId, String chunkId)
	{
		getMongoTemplate().findAndModify(Query.query(Criteria.where(ID).is(fileId)),
				new Update().push("chunks", new ChunkStatus(chunkId, true))
						.set("dateCreated", new Date()),
				FindAndModifyOptions.options().upsert(true), MongoLock.class);

	}


	public void setCompleteStatus(final String fileId, final String chunkId)
	{
		getMongoTemplate().findAndModify(Query.query(Criteria
						.where(ID).is(fileId)
						.and("chunks").elemMatch(Criteria.where("chunkId").is(chunkId))),
				new Update().set("chunks.$.status", false),
				FindAndModifyOptions.options().upsert(false), MongoLock.class);
	}

	public Set<ChunkStatus> findByFileId(final String fileId)
	{
		final Set<Set<ChunkStatus>> collect = getMongoTemplate().find(Query.query(Criteria.where(ID).is(fileId)), MongoLock.class)
				.stream()
				.map(MongoLock::getChunks)
				.collect(Collectors.toSet());

		return collect.stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}
}

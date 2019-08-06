package uk.co.floow.calculator.dao;

import java.util.Set;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.floow.calculator.domain.DocumentWordCount;
import uk.co.floow.calculator.domain.WordCount;

public class WordCountDao
{
	private MongoTemplate mongoTemplate;

	public WordCountDao(final MongoTemplate mongoTemplate)
	{
		this.mongoTemplate = mongoTemplate;
	}

	public void save(Set<WordCount> wordCount, String fileId)
	{
		this.mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(fileId)),
				new Update().addToSet("wordCounts", wordCount), FindAndModifyOptions.options().upsert(true), DocumentWordCount.class);

	}

	public Set<Set<WordCount>> findBy(String fileId)
	{
		return this.mongoTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)),DocumentWordCount.class).getWordCounts();
	}

}

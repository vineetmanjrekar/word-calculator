package uk.co.floow.calculator.dao;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import uk.co.floow.calculator.domain.DocumentWordCount;
import uk.co.floow.calculator.domain.WordCount;

@Component
public class WordCountDao extends MongoTemplateDao
{


	public void save(Set<WordCount> wordCount, String fileId)
	{
		getMongoTemplate().findAndModify(Query.query(Criteria.where("_id").is(fileId)),
				new Update().addToSet("wordCounts", wordCount), FindAndModifyOptions.options().upsert(true), DocumentWordCount.class);

	}

	public Set<Set<WordCount>> findBy(String fileId)
	{
		return getMongoTemplate().findOne(Query.query(Criteria.where("_id").is(fileId)),DocumentWordCount.class).getWordCounts();
	}

}

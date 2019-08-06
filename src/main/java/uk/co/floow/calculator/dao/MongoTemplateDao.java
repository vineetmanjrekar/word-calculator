package uk.co.floow.calculator.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import com.mongodb.MongoClient;

import lombok.Getter;

@Getter
public class MongoTemplateDao
{
	private MongoTemplate mongoTemplate;

	public MongoTemplateDao()
	{
		this.mongoTemplate = new MongoTemplate(new MongoClient(), "files");
	}


}

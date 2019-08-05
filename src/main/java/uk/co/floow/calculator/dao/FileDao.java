package uk.co.floow.calculator.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import uk.co.floow.calculator.domain.FileDocument;

@Component
public class FileDao
{
	private MongoTemplate mongoTemplate;

	public FileDao(final MongoTemplate mongoTemplate)
	{
		this.mongoTemplate = mongoTemplate;
	}

	public void save(FileDocument fileDocument)
	{
		mongoTemplate.save(fileDocument);
	}

}

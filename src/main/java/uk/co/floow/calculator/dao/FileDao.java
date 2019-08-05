package uk.co.floow.calculator.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Component;

import uk.co.floow.calculator.domain.FileDocument;
import uk.co.floow.calculator.domain.WordCount;

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

	public MapReduceResults<WordCount> mapReduce()
	{
		String map = "function() { var allLines = this.lines;  if (allLines) { " +
				"  for (var i = 0; i < allLines.length; i++) {  " +
				"singleLine = allLines[i].toLowerCase().split(\" \");  " +
				"if (singleLine) { " +
				"for (var j = singleLine.length - 1; j >= 0; j--) {  " +
				"         if (singleLine[j])  {  " +
				" emit(singleLine[j], 1);   " +
				"    }      } } }  }  };";

		String reduce = "function( key, values ) {    \n" +
				"    var count = 0;    \n" +
				"    values.forEach(function(v) {            \n" +
				"        count +=v;    \n" +
				"    });\n" +
				"    return count;\n" +
				"}";

		return mongoTemplate.mapReduce("fileDocument", map, reduce, WordCount.class);
	}
}

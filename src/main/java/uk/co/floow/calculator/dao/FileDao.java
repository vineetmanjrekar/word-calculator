package uk.co.floow.calculator.dao;

import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import uk.co.floow.calculator.domain.FileDocument;
import uk.co.floow.calculator.domain.WordCount;

@Component
public class FileDao extends MongoTemplateDao
{
	public void save(FileDocument fileDocument)
	{
		getMongoTemplate().save(fileDocument);
	}

	public MapReduceResults<WordCount> mapReduce(final String fileId)
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

		return getMongoTemplate().mapReduce(Query.query(Criteria.where("fileId").is(fileId)),
				"fileDocument", map, reduce, WordCount.class);
	}
}

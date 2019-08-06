package uk.co.floow.calculator.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Component;

import com.google.common.collect.Streams;

import uk.co.floow.calculator.dao.FileDao;
import uk.co.floow.calculator.dao.WordCountDao;
import uk.co.floow.calculator.domain.WordCount;

@Component
public class WordCalculatorService
{

	private FileDao fileDao;
	private WordCountDao wordCountDao;

	@Autowired
	public WordCalculatorService(final FileDao fileDao, final WordCountDao wordCountDao)
	{
		this.fileDao = fileDao;
		this.wordCountDao = wordCountDao;
	}

	public void calculateWordCount(String fileId) {
		final MapReduceResults<WordCount> wordCounts = this.fileDao.mapReduce(fileId);
		final Set<WordCount> wordCountSet = Streams.stream(wordCounts)
				.collect(Collectors.toSet());
		this.wordCountDao.save(wordCountSet, fileId);

	}
}

package uk.co.floow.calculator.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import uk.co.floow.calculator.dao.FileDao;
import uk.co.floow.calculator.dao.WordCountDao;
import uk.co.floow.calculator.domain.FileDocument;
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

	public Map<String, Integer> getWordCountsFor(final String fileId)
	{
		return this.wordCountDao.findBy(fileId).stream()
				.flatMap(Collection::stream)
				.collect(Collectors.groupingBy(WordCount::getId,Collectors.summingInt(WordCount::getValue)));
	}

	public void uploadFile(final String fileId, final MultipartFile file) throws IOException
	{
		int sizeOfFiles = 1024 * 1024 * 10;
		final byte[][] bytes = chunkArray(file.getBytes(), sizeOfFiles);

		Lists.newArrayList(bytes)
				.forEach(chunk -> {
					final String s = new String(chunk);
					final String[] lines = s.split("\n");
					fileDao.save(new FileDocument(fileId, chunk.toString(), Lists.newArrayList(lines)));
				});
	}

	private byte[][] chunkArray(byte[] array, int chunkSize)
	{
		int numOfChunks = (int) Math.ceil((double) array.length / chunkSize);
		byte[][] output = new byte[numOfChunks][];

		for (int i = 0; i < numOfChunks; ++i)
		{
			int start = i * chunkSize;
			int length = Math.min(array.length - start, chunkSize);

			byte[] temp = new byte[length];
			System.arraycopy(array, start, temp, 0, length);
			output[i] = temp;
		}

		return output;
	}

}


package uk.co.floow.calculator.resources;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uk.co.floow.calculator.service.WordCalculatorService;

@RestController
@RequestMapping("/word-calculator")
public class WordCountResource
{
	@Autowired
	private WordCalculatorService wordCalculatorService;


	@GetMapping("/word/count/{fileId}")
	public Map<String, Integer> getAllWordCounts(@PathVariable("fileId")String fileId)
	{
		return wordCalculatorService.getWordCountsFor(fileId);
	}

	@GetMapping("/word/count/{fileId}/{word}")
	public Integer getWordCountFor(@PathVariable("fileId") String fileId, @PathVariable("word") String word)
	{
		return wordCalculatorService.getWordCountsFor(fileId).get(word);
	}

	@GetMapping("/word/count/max/{fileId}")
	public Map.Entry<String, Integer> getWordWithMaxCount(@PathVariable("fileId") String fileId)
	{
		final Map<String, Integer> countMap = wordCalculatorService.getWordCountsFor(fileId);
		final Map.Entry<String, Integer> max = Collections.max(countMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue));
		return max;
	}

	@GetMapping("/word/count/min/{fileId}")
	public Map.Entry<String, Integer> getWordWithMinCount(@PathVariable("fileId") String fileId)
	{
		final Map<String, Integer> countMap = wordCalculatorService.getWordCountsFor(fileId);
		final Map.Entry<String, Integer> min = Collections.min(countMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue));
		return min;
	}

	@PostMapping("/process/word/count/{fileId}")
	public void startWordCountFor(@PathVariable("fileId") String fileId)
	{
		wordCalculatorService.calculateWordCount(fileId);
	}

	@PostMapping("/file/upload/{fileId}")
	public void uploadFile(@PathVariable("fileId") String fileId,
			@RequestParam("file") MultipartFile file) throws IOException
	{
		wordCalculatorService.uploadFile(fileId, file);
	}

}

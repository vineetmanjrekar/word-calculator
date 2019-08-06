package uk.co.floow.calculator.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/process/word/count/{fileId}")
	public void startWordCountFor(@PathVariable("fileId") String fileId)
	{
		wordCalculatorService.calculateWordCount(fileId);
	}
}

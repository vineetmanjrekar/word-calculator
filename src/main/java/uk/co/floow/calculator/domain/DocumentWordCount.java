package uk.co.floow.calculator.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DocumentWordCount
{
	@Id
	private String fileId;
	private Set<Set<WordCount>> wordCounts;
}

package uk.co.floow.calculator.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WordCount
{
	@Id
	private String id;
	private int value;

}

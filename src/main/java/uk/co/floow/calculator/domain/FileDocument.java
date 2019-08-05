package uk.co.floow.calculator.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDocument
{
	@Id
	private String fileId;
	private List<String> lines;



}

package uk.co.floow.calculator.domain;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileDocument
{
	@Indexed
	private String fileId;
	private String chunkId;
	private List<String> lines;



}

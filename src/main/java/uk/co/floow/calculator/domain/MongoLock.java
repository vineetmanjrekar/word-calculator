package uk.co.floow.calculator.domain;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MongoLock
{
	@Id
	private String fileId;
	private Set<ChunkStatus> chunks;

	@Field
	@Indexed(name="someDateFieldIndex", expireAfterSeconds=300)
	private Date dateCreated  = new Date();


}

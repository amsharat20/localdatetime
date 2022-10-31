package db.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Response {

	String applicationName;	

	public LocalDateTime eventStarted;

	public LocalDateTime eventEnded;

	public String eventStatus;

	public String duration;
	
	public String eventName;

}

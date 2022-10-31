package cloud.watch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import db.data.Response;
import response.Data;
import response.Downtime;
import response.HourlyStatus;
import response.MainResponseData;
import java.time.temporal.ChronoUnit;

public class CloudWatchCore {
	static List<MainResponseData> result = new ArrayList<MainResponseData>();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static List<MainResponseData> getHourlyStatus() {
		List<HourlyStatus> hourlyStatusList = new ArrayList<HourlyStatus>();
		List<Data> dataList = new ArrayList<Data>();
		
		Data data = new Data();
		MainResponseData responseData = new MainResponseData();
		
		Map<LocalDateTime,LocalDateTime> dateList = formData(); //Response Data
		List<Response> eventList  = getResponse();   //DB Data
		Long finalDowntime = 0L ;
		
		responseData.setTenantID("uuid-1");
		
		List<Downtime> downtimeList = new ArrayList<Downtime>();

		for (Map.Entry<LocalDateTime,LocalDateTime> givenDate : dateList.entrySet()) {
			LocalDateTime givenStartTime = givenDate.getKey(); //response
			LocalDateTime givenEndTime = givenDate.getValue();
			
			HourlyStatus hourlyStatus = new HourlyStatus();
			hourlyStatus.setDate(givenStartTime.format(formatter));
			
			//Sort the DB data.
			
			for(Response event: eventList) {   //Loop through DB Data ::eventList
				Downtime downtime = new Downtime();
				//check if it is Up and Running
				//if (event.eventStatus.equalsIgnoreCase("up")) {
				//	continue;
				//}
				//Set Downtime Status
				if (givenStartTime.isAfter(event.getEventStarted()) || givenStartTime.isEqual(event.getEventStarted())) {
					System.out.println("MapStartTime-  "+givenStartTime);
					System.out.println("MapEndTime-  "+givenEndTime);
					System.out.println("Event StartTime- "+event.getEventStarted());
					System.out.println("Event EndTime- "+event.getEventEnded());
					if (event.getEventEnded().isBefore(givenEndTime) ) {
						//Set Downtime Status
						downtime.setFrom(event.getEventStarted().format(formatter));
						downtime.setTo(event.getEventEnded().format(formatter));
						long minutes = ChronoUnit.MINUTES.between( event.getEventStarted(), event.getEventEnded());
						downtime.setTotalDownTime(minutes);
						finalDowntime = Long.sum(finalDowntime, minutes);
						downtimeList.add(downtime);
					}
				}
			}
			//Set Hourly Status
			hourlyStatus.setTotalDownTime(finalDowntime);  
			hourlyStatus.setDowntime(downtimeList);
			hourlyStatusList.add(hourlyStatus);
		}
		
		//Set Data 
		data.setApplicationName("web socket");
		data.setHourlyStatus(hourlyStatusList);
		dataList.add(data);
		
		responseData.setData(data);
		result.add(responseData);
		
		return result;
	}
	
	public static List<Response> getResponse() {
		List<Response> res = new ArrayList<Response>();

		Response response1 = new Response();
		response1.setApplicationName("rbac");
		response1.setDuration("00:30:00");
		response1.setEventStarted(LocalDateTime.parse("2022-10-19 09:00:00",formatter));
		response1.setEventEnded(LocalDateTime.parse("2022-10-19 09:30:00",formatter));
		response1.setEventStatus("down");
		response1.setEventName("event-1");

		Response response2 = new Response();
		response2.setApplicationName("rbac");
		response2.setDuration("00:15:00");
		response2.setEventStarted(LocalDateTime.parse("2022-10-19 09:30:00",formatter));
		response2.setEventEnded(LocalDateTime.parse("2022-10-19 09:45:00",formatter));
		response2.setEventStatus("down");
		response2.setEventName("event-2");
		
		Response response3 = new Response();
		response3.setApplicationName("rbac");
		response3.setDuration("00:15:00");
		response3.setEventStarted(LocalDateTime.parse("2022-10-19 09:45:00",formatter));
		response3.setEventEnded(LocalDateTime.parse("2022-10-19 10:00:00",formatter));
		response3.setEventStatus("down");
		response3.setEventName("event-3");
		
		Response response4 = new Response();
		response4.setApplicationName("rbac");
		response4.setDuration("00:45:00");
		response4.setEventStarted(LocalDateTime.parse("2022-10-19 10:00:00",formatter));
		response4.setEventEnded(LocalDateTime.parse("2022-10-19 10:45:00",formatter));
		response4.setEventStatus("down");
		response4.setEventName("event-4");
		
		Response response5 = new Response();
		response5.setApplicationName("rbac");
		response5.setDuration("00:15:00");
		response5.setEventStarted(LocalDateTime.parse("2022-10-19 10:45:00",formatter));
		response5.setEventEnded(LocalDateTime.parse("2022-10-19 10:55:00",formatter));
		response5.setEventStatus("down");
		response5.setEventName("event-5");
		
		Response response6 = new Response();
		response6.setApplicationName("rbac");
		response6.setDuration("00:15:00");
		response6.setEventStarted(LocalDateTime.parse("2022-10-19 10:55:00",formatter));
		response6.setEventEnded(LocalDateTime.parse("2022-10-19 11:00:00",formatter));
		response6.setEventStatus("down");
		response6.setEventName("event-6");
		
		Response response7 = new Response();
		response7.setApplicationName("rbac");
		response7.setDuration("02:00:00");
		response7.setEventStarted(LocalDateTime.parse("2022-10-19 11:00:00",formatter));
		response7.setEventEnded(LocalDateTime.parse("2022-10-19 12:10:00",formatter));
		response7.setEventStatus("down");
		response7.setEventName("event-7");
		
		Response response8 = new Response();
		response8.setApplicationName("rbac");
		response8.setDuration("02:00:00");
		response8.setEventStarted(LocalDateTime.parse("2022-10-19 12:10:00",formatter));
		response8.setEventEnded(LocalDateTime.parse("2022-10-19 12:30:00",formatter));
		response8.setEventStatus("down");
		response8.setEventName("event-8");

		res.add(response1);
		res.add(response2);
		res.add(response3);
		res.add(response4);
		//res.add(response5);
		//res.add(response6);
		//res.add(response7);
		//res.add(response8);
		return res;
	}
	
	private static Map formData() {
		Map<LocalDateTime,LocalDateTime> dateList = new LinkedHashMap<LocalDateTime, LocalDateTime>();
		dateList.put(LocalDateTime.parse("2022-10-19 09:00:00",formatter),LocalDateTime.parse("2022-10-19 10:00:00",formatter));
		dateList.put(LocalDateTime.parse("2022-10-19 10:00:00",formatter),LocalDateTime.parse("2022-10-19 11:00:00",formatter));
		dateList.put(LocalDateTime.parse("2022-10-19 11:00:00",formatter),LocalDateTime.parse("2022-10-19 12:00:00",formatter));
		dateList.put(LocalDateTime.parse("2022-10-19 12:00:00",formatter),LocalDateTime.parse("2022-10-19 13:00:00",formatter));
		return dateList;
	}

	public static void main(String[] args) throws JsonProcessingException {
		List<MainResponseData>  result = getHourlyStatus();
		
		ObjectMapper Obj = new ObjectMapper();
		String json = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(json); 
		
	}
}

package response;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

public class Data {
    private String applicationName;
    private List<HourlyStatus> hourlyStatus;

    @JsonProperty("applicationName")
    public String getApplicationName() { return applicationName; }
    @JsonProperty("applicationName")
    public void setApplicationName(String value) { this.applicationName = value; }

    @JsonProperty("hourlyStatus")
    public List<HourlyStatus> getHourlyStatus() { return hourlyStatus; }
    @JsonProperty("hourlyStatus")
    public void setHourlyStatus(List<HourlyStatus> hourlyStatusList) { this.hourlyStatus = hourlyStatusList; }
}
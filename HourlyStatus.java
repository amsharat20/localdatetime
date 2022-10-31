package response;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

public class HourlyStatus {
    private String date;
    private Long totalDownTime;
    private List<Downtime> downtime;

    @JsonProperty("date")
    public String getDate() { return date; }
    @JsonProperty("date")
    public void setDate(String value) { this.date = value; }

    @JsonProperty("totalDownTime")
    public Long getTotalDownTime() { return totalDownTime; }
    @JsonProperty("totalDownTime")
    public void setTotalDownTime(Long value) { this.totalDownTime = value; }

    @JsonProperty("downtime")
    public List<Downtime> getDowntime() { return downtime; }
    @JsonProperty("downtime")
    public void setDowntime(List<Downtime> downtimeList) { this.downtime = downtimeList; }
}
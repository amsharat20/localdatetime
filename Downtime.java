package response;

import com.fasterxml.jackson.annotation.*;

public class Downtime {
    private String from;
    private String to;
    private Long totalDownTime;

    @JsonProperty("from")
    public String getFrom() { return from; }
    @JsonProperty("from")
    public void setFrom(String value) { this.from = value; }

    @JsonProperty("to")
    public String getTo() { return to; }
    @JsonProperty("to")
    public void setTo(String value) { this.to = value; }

    @JsonProperty("totalDownTime")
    public Long getTotalDownTime() { return totalDownTime; }
    @JsonProperty("totalDownTime")
    public void setTotalDownTime(Long value) { this.totalDownTime = value; }
}

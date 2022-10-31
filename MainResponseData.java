package response;

import com.fasterxml.jackson.annotation.*;

public class MainResponseData {
    private String tenantID;
    private Data data;

    @JsonProperty("tenantId")
    public String getTenantID() { return tenantID; }
    @JsonProperty("tenantId")
    public void setTenantID(String value) { this.tenantID = value; }

    @JsonProperty("data")
    public Data getData() { return data; }
    @JsonProperty("data")
    public void setData(Data value) { this.data = value; }
}
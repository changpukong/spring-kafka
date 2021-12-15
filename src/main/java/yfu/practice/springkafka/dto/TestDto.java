package yfu.practice.springkafka.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TestDto {
    
    @JsonProperty("YfuName")
    @JsonAlias("YFU_NAME")
    private String yfuName;
    
    @JsonProperty("YfuBalance")
    @JsonAlias("YFU_BALANCE")
    private String yfuBalance;
    
    @JsonProperty("YfuDate")
    @JsonAlias("YFU_DATE")
    private String yfuDate;
    
    @JsonProperty("YfuTimestamp")
    @JsonAlias("YFU_TIMESTAMP")
    private String yfuTimestamp;
}
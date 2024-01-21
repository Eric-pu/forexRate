package org.exchange_rate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailyForeignExchangeRatesVo {

    @JsonProperty(value = "Date")
    private String date;
    @JsonProperty(value = "USD/NTD")
    private String usdToNtd;
    @JsonProperty(value = "RMB/NTD")
    private String rmbToNtd;
    @JsonProperty(value = "EUR/USD")
    private String eurToUsd;
    @JsonProperty(value = "USD/JPY")
    private String usdToJpy;
    @JsonProperty(value = "GBP/USD")
    private String gbpToUsd;
    @JsonProperty(value = "AUD/USD")
    private String audToUsd;
    @JsonProperty(value = "USD/HKD")
    private String usdToHkd;
    @JsonProperty(value = "USD/RMB")
    private String usdToRmb;
    @JsonProperty(value = "USD/ZAR")
    private String usdToZar;
    @JsonProperty(value = "NZD/USD")
    private String nzdToUsd;

}

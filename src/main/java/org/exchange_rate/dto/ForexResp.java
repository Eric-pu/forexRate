package org.exchange_rate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ForexResp extends BaseResponse{

    @JsonProperty(value = "currency")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CurrencyItem> currencyList;

    @Data
    public static class CurrencyItem {

        @JsonProperty(value = "date")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String date;

        @JsonProperty(value = "usd")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String usd;

        @JsonProperty(value = "ntd")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String ntd;

        @JsonProperty(value = "rbm")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String rbm;

        @JsonProperty(value = "eur")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String eur;

        @JsonProperty(value = "jpy")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String jpy;

        @JsonProperty(value = "gbp")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String gbp;

        @JsonProperty(value = "aup")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String aup;

        @JsonProperty(value = "hkd")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String hkd;

        @JsonProperty(value = "zar")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String zar;

        @JsonProperty(value = "nzd")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String nzd;

    }

}

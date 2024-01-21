package org.exchange_rate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseResponse {

    @JsonProperty(value = "error")
    private RespHead error;

    @Data
    public static class RespHead {

        public void setRespHead(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @JsonProperty(value = "code")
        private String code;

        @JsonProperty(value = "message")
        private String message;

    }

}

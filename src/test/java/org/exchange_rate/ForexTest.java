package org.exchange_rate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.exchange_rate.controller.ApiController;
import org.exchange_rate.dto.BaseResponse;
import org.exchange_rate.dto.ForexRequest;
import org.exchange_rate.dto.ForexResp;
import org.exchange_rate.enums.RespStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@SpringBootTest
public class ForexTest {

    @Autowired
    ApiController apiController;

    /**
     * 開始日期與結束日期皆在區間範圍那
     * @throws JsonProcessingException
     */
    @Test
    void forexTest_success() throws JsonProcessingException {

        ForexRequest request = new ForexRequest();
        request.setStartDate("2024/01/01");
        request.setEndDate("2024/01/11");
        request.setCurrency("usd");
        ForexResp actual = apiController.queryExchangeRate(request);

        ForexResp expected = new ForexResp();
        BaseResponse.RespHead head = new BaseResponse.RespHead();
        head.setRespHead(RespStateEnum.SUCCESS.getCode(), RespStateEnum.SUCCESS.getMsg());
        expected.setError(head);

        log.info(new ObjectMapper().writeValueAsString(actual));

        Assertions.assertEquals(expected.getError(), actual.getError());

    }

    /**
     * 輸入結束日期早於開始日期
     * @throws JsonProcessingException
     */
    @Test
    void forexTest_error_endDate_before_startDate() throws JsonProcessingException {

        ForexRequest request = new ForexRequest();
        request.setStartDate("2024/01/01");
        request.setEndDate("2023/12/31");
        request.setCurrency("usd");
        ForexResp actual = apiController.queryExchangeRate(request);

        ForexResp expected = new ForexResp();
        BaseResponse.RespHead head = new BaseResponse.RespHead();
        head.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());
        expected.setError(head);

        log.info(new ObjectMapper().writeValueAsString(actual));

        Assertions.assertEquals(expected.getError(), actual.getError());
    }

    /**
     * 輸入開始日期超過一年前以上
     * @throws JsonProcessingException
     */
    @Test
    void forexTest_error_startDate_more_than_one_year() throws JsonProcessingException {

        ForexRequest request = new ForexRequest();
        request.setStartDate("2022/12/01");
        request.setEndDate("2024/1/10");
        request.setCurrency("usd");
        ForexResp actual = apiController.queryExchangeRate(request);

        ForexResp expected = new ForexResp();
        BaseResponse.RespHead head = new BaseResponse.RespHead();
        head.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());
        expected.setError(head);

        log.info(new ObjectMapper().writeValueAsString(actual));

        Assertions.assertEquals(expected.getError(), actual.getError());
    }

    /**
     * 輸入結束日期為當日日期
     * @throws JsonProcessingException
     */
    @Test
    void forexTest_error_endDate_not_be_today() throws JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ForexRequest request = new ForexRequest();
        request.setStartDate("2024/01/01");
        request.setEndDate(sdf.format(new Date()));
        request.setCurrency("usd");
        ForexResp actual = apiController.queryExchangeRate(request);

        ForexResp expected = new ForexResp();
        BaseResponse.RespHead head = new BaseResponse.RespHead();
        head.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());
        expected.setError(head);

        log.info(new ObjectMapper().writeValueAsString(actual));

        Assertions.assertEquals(expected.getError(), actual.getError());
    }

}

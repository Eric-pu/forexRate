package org.exchange_rate;

import lombok.extern.slf4j.Slf4j;
import org.exchange_rate.controller.ApiController;
import org.exchange_rate.dto.ForexRequest;
import org.exchange_rate.dto.ForexResp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ForexTest {

    @Autowired
    ApiController apiController;

    @Test
    void forexTest() {
        try {
            ForexRequest request = new ForexRequest();
            request.setStartDate("2024/01/01");
            request.setEndDate("2024/01/11");
            request.setCurrency("usd");

            ForexResp resp = apiController.queryExchangeRate(request, "twd");

            log.info("===== Json 回傳內容 ===== ");
            log.info(resp.getError().toString());
            log.info(resp.getCurrencyList().toString());
        } catch (Exception e) {
            log.error("===== 外匯API發生錯誤 =====");
        }
    }

}

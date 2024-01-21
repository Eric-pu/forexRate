package org.exchange_rate;

import lombok.extern.slf4j.Slf4j;
import org.exchange_rate.service.BatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
public class BatchTest {

    @Autowired
    BatchService batchService;

    @Test
    void BatchApiJob() throws Exception {

        try {
            batchService.insertExchangeRate();
        } catch (Exception e) {
            log.error("===== 批次匯入資料發生錯誤 =====");
        }

    }

}

package org.exchange_rate.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.exchange_rate.entity.CollectionEntity;
import org.exchange_rate.repository.ForexRepository;
import org.exchange_rate.vo.DailyForeignExchangeRatesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BatchService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ForexRepository exchangeRateRepository;

    @Value("${batch.openapi}")
    String apiUrl;

    @Transactional(rollbackFor = Exception.class)
    public void insertExchangeRate() throws Exception {

        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();

        try {
            response = restTemplate.getForEntity(apiUrl, String.class);
        } catch (Exception e) {
            log.error("error : {}", e);
            throw new Exception("===== API 發生錯誤 =====");
        }

        try {
            if (!Objects.isNull(response)) {
                List<DailyForeignExchangeRatesVo> list = objectMapper.readValue(response.getBody(), new TypeReference<List<DailyForeignExchangeRatesVo>>() {});

                List<String> allList = exchangeRateRepository.queryListOfDatetime();
                for (DailyForeignExchangeRatesVo item :list){

                    if (allList.contains(item.getDate())) {
                        CollectionEntity updateEntity = exchangeRateRepository.findByDatetime(item.getDate()).orElse(null);

                        updateEntity.setUsdToNtd(item.getUsdToNtd());
                        updateEntity.setRmbToNtd(item.getRmbToNtd());
                        updateEntity.setEurToUsd(item.getEurToUsd());
                        updateEntity.setUsdToJpy(item.getUsdToJpy());
                        updateEntity.setGbpToUsd(item.getGbpToUsd());
                        updateEntity.setAupToUsd(item.getAudToUsd());
                        updateEntity.setUsdToHkd(item.getUsdToHkd());
                        updateEntity.setUsdToRmb(item.getUsdToRmb());
                        updateEntity.setUsdToZar(item.getUsdToZar());
                        updateEntity.setNzdToUsd(item.getNzdToUsd());
                        updateEntity.setUpdateDt(LocalDateTime.now());
                        updateEntity.setUpdateBy("SYSTEM");

                        exchangeRateRepository.save(updateEntity);
                    } else {
                        //接下來只針對需求 美金/台幣 欄位進行取值
                        CollectionEntity insertEntity = new CollectionEntity();
                        insertEntity.setId(System.currentTimeMillis());
                        insertEntity.setDate(item.getDate());
                        insertEntity.setUsdToNtd(item.getUsdToNtd());
                        insertEntity.setRmbToNtd(item.getRmbToNtd());
                        insertEntity.setEurToUsd(item.getEurToUsd());
                        insertEntity.setUsdToJpy(item.getUsdToJpy());
                        insertEntity.setGbpToUsd(item.getGbpToUsd());
                        insertEntity.setAupToUsd(item.getAudToUsd());
                        insertEntity.setUsdToHkd(item.getUsdToHkd());
                        insertEntity.setUsdToRmb(item.getUsdToRmb());
                        insertEntity.setUsdToZar(item.getUsdToZar());
                        insertEntity.setNzdToUsd(item.getNzdToUsd());
                        insertEntity.setCreateDt(LocalDateTime.now());
                        insertEntity.setCreateBy("SYSTEM");

                        exchangeRateRepository.save(insertEntity);
                    }

                }

            } else {
                throw new Exception("===== 今日產生無資料 =====");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("===== 批次發生錯誤 =====");
        }


    }

}

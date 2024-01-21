package org.exchange_rate.service;

import org.exchange_rate.dto.BaseResponse;
import org.exchange_rate.dto.ForexRequest;
import org.exchange_rate.dto.ForexResp;
import org.exchange_rate.entity.CollectionEntity;
import org.exchange_rate.enums.RespStateEnum;
import org.exchange_rate.repository.ForexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForexService {

    @Autowired
    ForexRepository forexRepository;

    public ForexResp queryExchangeRate(ForexRequest request, String currency){

        try {
            //打台幣API 輸入幣別美金 查詢 usd to ntd
            //打台幣API 輸入幣別人民幣 查詢 rmb to ntd

            //將請求參數 時間 進行處理
            //1. 時間格式 yyyy/MM/dd 轉換 yyyy-MM-dd
            //2. 結束時間 轉換成 LocalDate ，並減去一天，再還原成字串
            String startDatestr = request.getStartDate().replaceAll("/", "-");
            String endDatestr = request.getEndDate().replaceAll("/", "-");

            LocalDate startDate = LocalDate.parse(startDatestr);
            LocalDate endDate = LocalDate.parse(endDatestr).minusDays(1);

            if (endDate.isBefore(startDate)){

                BaseResponse.RespHead respHead = new BaseResponse.RespHead();
                ForexResp resp = new ForexResp();

                respHead.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());

                resp.setError(respHead);
                System.out.println("2131231");
                return resp;

            }

            List<CollectionEntity> collectionEntities = forexRepository.queryByStartDateAndEndDate(startDate, endDate);

            //判斷API 查詢幣別 針對 外匯幣別 進行篩選
            List<ForexResp.CurrencyItem> currencyItems = filterForex(collectionEntities, request, currency);

            BaseResponse.RespHead respHead = new BaseResponse.RespHead();
            ForexResp resp = new ForexResp();

            respHead.setRespHead(RespStateEnum.SUCCESS.getCode(), RespStateEnum.SUCCESS.getMsg());

            resp.setError(respHead);
            resp.setCurrencyList(currencyItems);

            return resp;
        } catch (Exception e) {
            e.printStackTrace();

            BaseResponse.RespHead respHead = new BaseResponse.RespHead();
            ForexResp resp = new ForexResp();

            respHead.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());

            resp.setError(respHead);

            return resp;
        }
    }

    private List<ForexResp.CurrencyItem> filterForex(List<CollectionEntity> collectionEntityList, ForexRequest request, String currency){
        List<ForexResp.CurrencyItem> list = new ArrayList<>();
        for (CollectionEntity simpleResult : collectionEntityList) {

            ForexResp.CurrencyItem item = new ForexResp.CurrencyItem();

            if ("usd".equals(request.getCurrency())) {
                item.setUsd(simpleResult.getUsdToNtd());
            }

            if ("twd".equals(currency)) {
                switch (request.getCurrency()){
                    case "usd":
                        item.setDate(simpleResult.getDate());
                        item.setUsd(simpleResult.getUsdToNtd());
                        break;
                    case "rmb":
                        item.setDate(simpleResult.getDate());
                        item.setRbm(simpleResult.getRmbToNtd());
                        break;
                }
            }

            if ("usd".equals(currency)) {
                switch (request.getCurrency()){
                    case "eur":
                        item.setDate(simpleResult.getDate());
                        item.setEur(simpleResult.getEurToUsd());
                        break;
                    case "gpb":
                        item.setDate(simpleResult.getDate());
                        item.setGbp(simpleResult.getGbpToUsd());
                        break;
                    case "aup":
                        item.setDate(simpleResult.getDate());
                        item.setAup(simpleResult.getAupToUsd());
                        break;
                    case "nzd":
                        item.setDate(simpleResult.getDate());
                        item.setNzd(simpleResult.getNzdToUsd());
                        break;
                }
            }

            if ("jpy".equals(currency)) {
                switch (request.getCurrency()){
                    case "usd":
                        item.setDate(simpleResult.getDate());
                        item.setUsd(simpleResult.getUsdToJpy());
                        break;
                }
            }

            if ("rmb".equals(currency)) {
                switch (request.getCurrency()) {
                    case "usd" :
                        item.setDate(simpleResult.getDate());
                        item.setUsd(simpleResult.getUsdToRmb());
                        break;
                }
            }

            if ("zar".equals(currency)) {
                switch (request.getCurrency()) {
                    case "usd" :
                        item.setDate(simpleResult.getDate());
                        item.setUsd(simpleResult.getUsdToZar());
                        break;
                }
            }

            list.add(item);
        }
        return list;
    }

}

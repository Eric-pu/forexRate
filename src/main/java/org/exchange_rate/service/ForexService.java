package org.exchange_rate.service;

import org.exchange_rate.dto.BaseResponse;
import org.exchange_rate.dto.ForexRequest;
import org.exchange_rate.dto.ForexResp;
import org.exchange_rate.entity.CollectionEntity;
import org.exchange_rate.enums.RespStateEnum;
import org.exchange_rate.repository.ForexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ForexService {

    @Autowired
    ForexRepository forexRepository;

    public ForexResp queryExchangeRate(ForexRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //打台幣API 輸入幣別美金 查詢 usd to ntd
            //打台幣API 輸入幣別人民幣 查詢 rmb to ntd

            //將請求參數 時間 進行處理
            //1. 時間格式 yyyy/MM/dd 轉換 yyyy-MM-dd
            //2. 結束時間 轉換成 LocalDate ，並減去一天，再還原成字串
            String startDateStr = request.getStartDate().replaceAll("/", "-");
            String endDateStr = request.getEndDate().replaceAll("/", "-");

            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            //比較開始日期跟結束日期是否超過一年以上
            long interval = getInterval(startDate, endDate);

            if (sdf.format(endDate).equals(sdf.format(new Date()))){

                BaseResponse.RespHead respHead = new BaseResponse.RespHead();
                ForexResp resp = new ForexResp();

                respHead.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());
                resp.setError(respHead);

                return resp;
            } else if (endDate.before(startDate)){

                BaseResponse.RespHead respHead = new BaseResponse.RespHead();
                ForexResp resp = new ForexResp();

                respHead.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());
                resp.setError(respHead);

                return resp;

            } else if (interval >= 365) {

                BaseResponse.RespHead respHead = new BaseResponse.RespHead();
                ForexResp resp = new ForexResp();

                respHead.setRespHead(RespStateEnum.ERROR.getCode(), RespStateEnum.ERROR.getMsg());
                resp.setError(respHead);

                return resp;

            }

            List<CollectionEntity> collectionEntities = forexRepository.queryByStartDateAndEndDate(startDate, endDate);

            //判斷API 查詢幣別 針對 外匯幣別 進行篩選
            List<ForexResp.CurrencyItem> currencyItems = filterForex(collectionEntities, request);

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

    private List<ForexResp.CurrencyItem> filterForex(List<CollectionEntity> collectionEntityList, ForexRequest request){
        List<ForexResp.CurrencyItem> list = new ArrayList<>();
        for (CollectionEntity simpleResult : collectionEntityList) {

            ForexResp.CurrencyItem item = new ForexResp.CurrencyItem();

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

            list.add(item);
        }
        return list;
    }

    /**
     * 判斷開始日期、結束日期 的 間隔時間
     * @param startDate
     * @param endDate
     * @return
     */
    private long getInterval(Date startDate, Date endDate){
        long day = 0;
        day = endDate.getTime() - startDate.getTime();
        return day / (24 * 60 * 60 * 1000);
    }

}

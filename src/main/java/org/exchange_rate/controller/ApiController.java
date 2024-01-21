package org.exchange_rate.controller;

import lombok.extern.slf4j.Slf4j;
import org.exchange_rate.dto.ForexRequest;
import org.exchange_rate.dto.ForexResp;
import org.exchange_rate.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class ApiController {

    @Autowired
    private ForexService forexService;

    @ResponseBody
    @RequestMapping(value = "/api/queryForex/{currency}", method = RequestMethod.POST)
    public ForexResp queryExchangeRate(@RequestBody ForexRequest request, @PathVariable String currency){
        return forexService.queryExchangeRate(request, currency);
    }

}

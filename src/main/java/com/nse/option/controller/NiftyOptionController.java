package com.nse.option.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.nifty.OptionChainResponse;
import com.nse.option.service.NiftyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NiftyOptionController
{
    @Value("${app.allowed-strikes}")
    private List<Integer> allowedStrikes;

    @Autowired
    NiftyService niftyService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/niftyData", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOptionData()
    {
        OptionChainResponse optionChainResponse = null;
        String responseString = "";
        try
        {
            optionChainResponse = niftyService.getNiftyOptionData();
            responseString = objectMapper.writeValueAsString(optionChainResponse);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return responseString;
    }

}

package com.nse.option.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.callput.MarketSnapshot;
import com.nse.option.model.iv.ScreenerResponse;
import com.nse.option.model.iv.StockCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
public class VolatilityController
{
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/screener")
    public ScreenerResponse getOptionData()
    {
        ScreenerResponse screenerResponse = null;
        System.out.println("Entering Screener");
        try
        {
            screenerResponse = objectMapper.readValue(getData(), ScreenerResponse.class);
            List<List<Object>> tableData  = screenerResponse.getBody().getTableData();

            for (List<Object> row : tableData)
            {
                String optionType = (String) row.get(1);
                Double strikePrice = (Double) row.get(2);
                Double ltp = (Double) row.get(3);
                Double dayChange = (Double) row.get(4);

                System.out.println(
                                optionType + " | " +
                                strikePrice + " | " +
                                ltp + " | " +
                                dayChange
                );

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return screenerResponse;
    }



    private String getData() {
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.lineSeparator();

        try (FileReader fr = new FileReader(ResourceUtils.getFile("classpath:MasterOI.json"));
             BufferedReader reader = new BufferedReader(fr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading data: " + e.getMessage();
        }
    }
}
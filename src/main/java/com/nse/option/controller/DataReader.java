package com.nse.option.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.callput.MarketSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
public class DataReader
{
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.allowed-strikes}")
    private List<Integer> allowedStrikes;

    @GetMapping("/data")
    public MarketSnapshot getOptionData()
    {
        MarketSnapshot marketSnapshot = null;

        try
        {
            marketSnapshot = objectMapper.readValue(getData(), MarketSnapshot.class);

            // Filter strikePriceList
            List<Integer> list = marketSnapshot.getBody().getOverallData().getStrikePriceList();
            list.retainAll(allowedStrikes);

            // Filter oiData
            marketSnapshot.getBody().getOiData().entrySet().removeIf(entry ->
                    !allowedStrikes.contains(Integer.parseInt(entry.getKey()))
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return marketSnapshot;
    }

    private String getData()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.lineSeparator();

        try (FileReader fr = new FileReader(ResourceUtils.getFile("classpath:OI.json"));
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

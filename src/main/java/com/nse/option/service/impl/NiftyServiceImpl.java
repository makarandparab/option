package com.nse.option.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.nifty.OptionChainResponse;
import com.nse.option.service.NiftyService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class NiftyServiceImpl implements NiftyService
{
    @Value("${nifty.cookie.value}")
    private String cookieValue;

    @Value("${app.allowed-strikes}")
    private List<Integer> allowedStrikes;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public OptionChainResponse getNiftyOptionData()
    {
        String body = "";
        OptionChainResponse optionChainResponse = null;

        try
        {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://www.nseindia.com/api/NextApi/apiClient/GetQuoteApi")
                    .queryParam("functionName", "getOptionChainData")
                    .queryParam("symbol", "NIFTY")
                    .queryParam("params", "expiryDate%3D13-Jan-2026" )
                    .build(true)
                    .toUriString();
            System.out.println("The URL is :: " + url);

            // Build client with timeouts and NO auto-follow redirects (keeps headers intact)
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(Timeout.ofDays(10_000)) // ms
                    .setResponseTimeout(Timeout.ofDays(20_000)) // ms
                    .build();

            try (CloseableHttpClient client = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .disableRedirectHandling() // important when you need to preserve headers like Cookie
                    .build())
            {
                HttpGet get = new HttpGet(url);

                // Custom headers
                get.addHeader(HttpHeaders.ACCEPT, "application/json, text/plain, */*");
                get.addHeader(HttpHeaders.COOKIE, cookieValue);
                get.addHeader(HttpHeaders.REFERER, "https://www.nseindia.com");
                get.addHeader(HttpHeaders.USER_AGENT,
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                                + "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");


                try (CloseableHttpResponse response = client.execute(get))
                {
                    int status = response.getCode();
                    body = response.getEntity() != null
                            ? EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
                            : "";

                    System.out.println("Status: " + status);
                    System.out.println("=== Body ===");
                    System.out.println(body);

                    optionChainResponse = objectMapper.readValue(body, OptionChainResponse.class);

                    // Filter strikePrice
                    optionChainResponse.getData().removeIf(optionDataRow -> !allowedStrikes.contains(optionDataRow.getStrikePrice()));
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return optionChainResponse;
    }
}

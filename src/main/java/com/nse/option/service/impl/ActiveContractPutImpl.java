package com.nse.option.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.iv.ScreenerResponse;
import com.nse.option.service.ActiveContractPutService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Service
public class ActiveContractPutImpl implements ActiveContractPutService
{

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.trendlyn.put.url}")
    private String externalApiUrl;

    @Value("${api.csrf.token}")
    private String csrftoken;

    @Value("${api.expiry.week}")
    private String expiryWeek;

    @Value("${api.market.type}")
    private String marketType;

    @Value("${api.put.screenType}")
    private String screenType;

    @Override
    public ScreenerResponse getActiveContractPut()
    {
        ScreenerResponse screenerResponse = null;
        String body = "";

        try
        {
            String sessionId = "";

            String url = UriComponentsBuilder
                    .fromHttpUrl(externalApiUrl)
                    .queryParam("mtype", marketType)
                    .queryParam("expDate", expiryWeek)
                    .queryParam("screenType", screenType )
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
                get.addHeader("Accept", "application/json, text/plain, */*");
                get.addHeader("User-Agent", "Mozilla/5.0 (Apache HttpClient 5)");
                get.addHeader("Referer", "https://smartoptions.trendlyne.com/");
                get.addHeader("Cookie", "csrftoken=" + csrftoken + (sessionId.isEmpty() ? "" : "; sessionid=" + sessionId));
                get.addHeader("X-CSRFToken", csrftoken);

                try (CloseableHttpResponse response = client.execute(get))
                {
                    int status = response.getCode();
                    body = response.getEntity() != null
                            ? EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
                            : "";

                    System.out.println("Status: " + status);
                    //System.out.println("=== Body ===");
                    //System.out.println(body);

                    screenerResponse = objectMapper.readValue(body, ScreenerResponse.class);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return screenerResponse;
    }
}

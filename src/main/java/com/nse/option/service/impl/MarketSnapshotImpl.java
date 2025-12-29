package com.nse.option.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.callput.MarketSnapshot;
import com.nse.option.service.MarketSnapshotService;
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
public class MarketSnapshotImpl implements MarketSnapshotService
{
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.trendlyn.url}")
    private String externalApiUrl;

    @Value("${api.csrf.token}")
    private String csrftoken;

    @Value("${api.expiry.week}")
    private String expiryWeek;


    public MarketSnapshot getMarketSnapshot()
    {
        MarketSnapshot marketSnapshot = null;
        String body = "";

        try
        {
            String sessionId = "";

            String url = UriComponentsBuilder
                    .fromHttpUrl(externalApiUrl)
                    .queryParam("stockId", "1887")
                    .queryParam("expDateList", expiryWeek)
                    .queryParam("minTime", "9%3A15")
                    .queryParam("maxTime", "15%3A30")
                    .queryParam("minStrikePrice", "24750")
                    .queryParam("maxStrikePrice", "27350")
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

                    marketSnapshot = objectMapper.readValue(body, MarketSnapshot.class);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return marketSnapshot;
    }

    /*
    private String getNifty()
    {
        String sessionId = "";
        String body = "";
        String niftyValue = "";

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
            HttpGet get = new HttpGet("https://smartoptions.trendlyne.com/phoenix/api/dashboard/all?fnoType=options&expDate=2025-12-30");

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
                System.out.println("=== Body ===");
                System.out.println(body);

                Dashboard dashboard = objectMapper.readValue(body, Dashboard.class);
                Map<String, IndexOverview> overview = dashboard.getBody().getOverViewData();

                IndexOverview indexOverview = overview.get("NIFTY");
                System.out.println(indexOverview.getIndexData().getValue());
                niftyValue = String.valueOf(indexOverview.getIndexData().getValue());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return niftyValue;
    }
    */

}

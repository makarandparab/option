package com.nse.option.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.option.model.callput.CallInfo;
import com.nse.option.model.callput.MarketSnapshot;
import com.nse.option.model.callput.OiStrikeData;
import com.nse.option.model.callput.PutInfo;
import com.nse.option.model.iv.ScreenerResponse;
import com.nse.option.service.ActiveContractCallService;
import com.nse.option.service.ActiveContractPutService;
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
import java.util.List;
import java.util.Map;


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

    @Value("${api.marketsnapshot.stockId}")
    private String stockId;

    @Value("${api.marketsnapshot.minTime}")
    private String minTime;

    @Value("${api.marketsnapshot.maxTime}")
    private String maxTime;

    @Value("${api.marketsnapshot.minStrikePrice}")
    private String minStrikePrice;

    @Value("${api.marketsnapshot.maxStrikePrice}")
    private String maxStrikePrice;

    @Autowired
    private ActiveContractPutService activeContractPutService;

    @Autowired
    private ActiveContractCallService activeContractCallService;

    public MarketSnapshot getMarketSnapshot()
    {
        MarketSnapshot marketSnapshot = null;
        String body = "";

        try
        {
            String sessionId = "";

            String url = UriComponentsBuilder
                    .fromHttpUrl(externalApiUrl)
                    .queryParam("stockId", stockId)
                    .queryParam("expDateList", expiryWeek)
                    .queryParam("minTime", minTime)
                    .queryParam("maxTime", maxTime)
                    .queryParam("minStrikePrice", minStrikePrice)
                    .queryParam("maxStrikePrice", maxStrikePrice)
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

    @Override
    public void enrichSnapshot(MarketSnapshot marketSnapshot)
    {
        if (marketSnapshot == null || marketSnapshot.getBody() == null) return;

        try
        {
            // Set niftyData based on actual spot price for accurate trend calculation
            if (marketSnapshot.getBody().getOverallData() != null) {
                double spotPrice = marketSnapshot.getBody().getOverallData().getSpotPrice();
                marketSnapshot.setNiftyData(String.valueOf(spotPrice));
            }

            ScreenerResponse screenerResponsePut = activeContractPutService.getActiveContractPut();
            ScreenerResponse screenerResponseCall = activeContractCallService.getActiveContractCall();

            setPutValues(marketSnapshot, screenerResponsePut);
            setCallValues(marketSnapshot, screenerResponseCall);
        }
        catch (Exception e)
        {
            System.err.println("Error enriching snapshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setCallValues(MarketSnapshot marketSnapshot, ScreenerResponse screenerResponseCall)
    {
        if (screenerResponseCall == null || screenerResponseCall.getBody() == null)
            return;

        List<List<Object>> tableData = screenerResponseCall.getBody().getTableData();
        for (Map.Entry<String, OiStrikeData> entry : marketSnapshot.getBody().getOiData().entrySet())
        {
            for (List<Object> row : tableData)
            {
                int strikePrice = toInt(row.get(2));
                int key = Integer.parseInt(entry.getKey());

                if (key == strikePrice)
                {
                    CallInfo callInfo = new CallInfo();
                    callInfo.setOpt_type(toString(row.get(1)));
                    callInfo.setStrike_price(toDouble(row.get(2)));
                    callInfo.setCurrent_price(toDouble(row.get(3)));
                    callInfo.setDay_change_percent(toDouble(row.get(4)));
                    callInfo.setVolume(toInt(row.get(5)));
                    callInfo.setTraded_contracts_change(toDouble(row.get(6)));
                    callInfo.setTotal_traded_value(toDouble(row.get(7)));
                    callInfo.setOpen_interest(toDouble(row.get(8)));
                    callInfo.setOpen_interest_chg(toDouble(row.get(9)));
                    callInfo.setOi_change_percent(toDouble(row.get(10)));
                    callInfo.setImplied_volatility(toDouble(row.get(11)));
                    callInfo.setIv_change_percent(toDouble(row.get(12)));
                    callInfo.setSpot_strike_price(toDouble(row.get(13)));
                    callInfo.setDelta_calc(toDouble(row.get(14)));
                    callInfo.setGamma_calc(toDouble(row.get(15)));
                    callInfo.setRho_calc(toDouble(row.get(16)));
                    callInfo.setTheta_calc(toDouble(row.get(17)));
                    callInfo.setVega_calc(toDouble(row.get(18)));
                    callInfo.setGet_built_up_str(toString(row.get(19)));
                    entry.getValue().setCallInfo(callInfo);

                    marketSnapshot.setSpotStrikePrice(String.valueOf(toDouble(row.get(13))));
                }
            }
        }
    }

    private void setPutValues(MarketSnapshot marketSnapshot, ScreenerResponse screenerResponsePut)
    {
        if (screenerResponsePut == null || screenerResponsePut.getBody() == null)
            return;

        List<List<Object>> tableData = screenerResponsePut.getBody().getTableData();
        for (Map.Entry<String, OiStrikeData> entry : marketSnapshot.getBody().getOiData().entrySet())
        {
            for (List<Object> row : tableData)
            {
                int strikePrice = toInt(row.get(2));
                int key = Integer.parseInt(entry.getKey());

                if (key == strikePrice)
                {
                    PutInfo putInfo = new PutInfo();
                    putInfo.setOpt_type(toString(row.get(1)));
                    putInfo.setStrike_price(toDouble(row.get(2)));
                    putInfo.setCurrent_price(toDouble(row.get(3)));
                    putInfo.setDay_change_percent(toDouble(row.get(4)));
                    putInfo.setVolume(toInt(row.get(5)));
                    putInfo.setTraded_contracts_change(toDouble(row.get(6)));
                    putInfo.setTotal_traded_value(toDouble(row.get(7)));
                    putInfo.setOpen_interest(toDouble(row.get(8)));
                    putInfo.setOpen_interest_chg(toDouble(row.get(9)));
                    putInfo.setOi_change_percent(toDouble(row.get(10)));
                    putInfo.setImplied_volatility(toDouble(row.get(11)));
                    putInfo.setIv_change_percent(toDouble(row.get(12)));
                    putInfo.setSpot_strike_price(toDouble(row.get(13)));
                    putInfo.setDelta_calc(toDouble(row.get(14)));
                    putInfo.setGamma_calc(toDouble(row.get(15)));
                    putInfo.setRho_calc(toDouble(row.get(16)));
                    putInfo.setTheta_calc(toDouble(row.get(17)));
                    putInfo.setVega_calc(toDouble(row.get(18)));
                    putInfo.setGet_built_up_str(toString(row.get(19)));
                    entry.getValue().setPutInfo(putInfo);
                }
            }
        }
    }

    private double toDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return 0.0;
    }

    private int toInt(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    private String toString(Object obj) {
        return obj != null ? obj.toString() : "";
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

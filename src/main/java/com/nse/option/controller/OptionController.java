package com.nse.option.controller;

import com.nse.option.model.callput.CallInfo;
import com.nse.option.model.callput.MarketSnapshot;
import com.nse.option.model.callput.OiStrikeData;
import com.nse.option.model.callput.PutInfo;
import com.nse.option.model.iv.ScreenerResponse;
import com.nse.option.service.*;
import com.nse.option.util.OptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OptionController {

    //@Value("${app.allowed-strikes}")
    private List<Integer> allowedStrikes;

    @Autowired
    MarketSnapshotService marketSnapshotService;

    @Autowired
    ActiveContractPutService activeContractPutService;

    @Autowired
    ActiveContractCallService activeContractCallService;

    @Autowired
    SnapshotHistoryService snapshotHistoryService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    NiftyService niftyService;

    @GetMapping(value = "/optionData")
    @ResponseBody
    public MarketSnapshot getOptionData() {
        String niftyValue = "";
        MarketSnapshot marketSnapshot = null;
        ScreenerResponse screenerResponsePut = null;
        ScreenerResponse screenerResponseCall = null;
        double pcr = 0.0;

        try
        {
            niftyValue = niftyService.getNiftyData();
            System.out.println("Nifty Value today ::: " + niftyValue);

            allowedStrikes = OptionUtil.getAllowedList(niftyValue);
            System.out.println(allowedStrikes);

            marketSnapshot = marketSnapshotService.getMarketSnapshot();
            System.out.println("DEBUG: marketSnapshot fetched: " + (marketSnapshot != null));

            if (marketSnapshot != null && marketSnapshot.getBody() != null
                    && marketSnapshot.getBody().getOverallData() != null)
            {
                pcr = marketSnapshot.getBody().getOverallData().getTotalPCR();
                //System.out.println("DEBUG: PCR: " + pcr);

                calculateMaxPain(marketSnapshot);

                List<Integer> list = marketSnapshot.getBody().getOverallData().getStrikePriceList();
                //System.out.println("DEBUG: Strike list size before filter: " + (list != null ? list.size() : "NULL"));
                if (list != null && allowedStrikes != null)
                {
                    try
                    {
                        list.retainAll(allowedStrikes);
                        //System.out.println("DEBUG: Strike list size after filter: " + list.size());
                    }
                    catch (Exception e)
                    {
                        System.err.println("DEBUG: retainAll FAILED: " + e.getMessage());
                    }
                }

                calculateResistanceSupport(marketSnapshot);

                if (marketSnapshot.getBody().getOiData() != null)
                {
                    marketSnapshot.getBody().getOiData().entrySet()
                            .removeIf(entry -> !allowedStrikes.contains(Integer.parseInt(entry.getKey())));
                    //System.out.println("DEBUG: OiData filtered");
                }
            }
            else {
                System.err.println("DEBUG: marketSnapshot or its body/overallData is NULL");
            }

            //calculateResistanceSupport(marketSnapshot);

            screenerResponsePut = activeContractPutService.getActiveContractPut();
            // System.out.println(screenerResponsePut.getHead().getStatus());

            screenerResponseCall = activeContractCallService.getActiveContractCall();
            // System.out.println(screenerResponseCall.getHead().getStatus());

            // Newly added code to update marketSnapshot object
            setPutValues(marketSnapshot, screenerResponsePut);
            setCallValues(marketSnapshot, screenerResponseCall);

            // Enrich with historical data
            enrichWithHistory(marketSnapshot, 5);
            enrichWithHistory(marketSnapshot, 15);

            //niftyValue = (marketSnapshot != null) ? marketSnapshot.getSpotStrikePrice() : null;
            //System.out.println("@@@@ :::  " + niftyValue);

            if (marketSnapshot != null)
            {
                marketSnapshot.setNiftyData(niftyValue != null ? niftyValue : "0");
                marketSnapshot.setNiftyValue((niftyValue != null ? niftyValue : "N/A") + " - (" + String.format("%.2f", pcr) + ")");
            }
        }
        catch (Exception e)
        {
            System.err.println("Error in getOptionData: " + e.getMessage());
            e.printStackTrace();
        }

        return marketSnapshot;
    }

    @GetMapping(value = "/index-jsp")
    public String getIndexJsp(Model model)
    {
        try
        {
            MarketSnapshot marketSnapshot = getOptionData();
            String priceTrend = "STABLE";

            try
            {
                // Get snapshot from 5 minutes ago to determine trend
                MarketSnapshot pastSnapshot = snapshotHistoryService.getSnapshotAt(5);

                // If 5 min ago is not available, try to get the most recent one (e.g. 2-10 mins range)
                if (pastSnapshot == null) {
                    for (int i = 2; i <= 10; i++) {
                        if (i == 5) continue;
                        pastSnapshot = snapshotHistoryService.getSnapshotAt(i);
                        if (pastSnapshot != null) break;
                    }
                }

                if (pastSnapshot != null && marketSnapshot != null &&
                        marketSnapshot.getNiftyData() != null && pastSnapshot.getNiftyData() != null) {
                    try {
                        double currentPrice = Double.parseDouble(marketSnapshot.getNiftyData());
                        double pastPrice = Double.parseDouble(pastSnapshot.getNiftyData());

                        if (currentPrice > pastPrice)
                            priceTrend = "UP";
                        else if (currentPrice < pastPrice)
                            priceTrend = "DOWN";
                    } catch (NumberFormatException nfe) {
                        System.err.println("Invalid price format: " + nfe.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error calculating trend: " + e.getMessage());
            }

            String dataJson = mapper.writeValueAsString(marketSnapshot);

            model.addAttribute("jspDataJson", dataJson);
            model.addAttribute("jspPriceTrend", priceTrend);
            return "dashboard";
        } catch (Exception e) {
            System.err.println("CRITICAL_ERROR in getIndexJsp: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("jspDataJson", "{}");
            model.addAttribute("jspPriceTrend", "STABLE");
            return "dashboard";
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

    private void enrichWithHistory(MarketSnapshot currentSnapshot, int minutesAgo) {
        MarketSnapshot pastSnapshot = snapshotHistoryService.getSnapshotAt(minutesAgo);
        if (pastSnapshot == null)
            return;

        Map<String, OiStrikeData> currentOiData = currentSnapshot.getBody().getOiData();
        Map<String, OiStrikeData> pastOiData = pastSnapshot.getBody().getOiData();

        for (Map.Entry<String, OiStrikeData> entry : currentOiData.entrySet()) {
            String strike = entry.getKey();
            OiStrikeData currentData = entry.getValue();
            OiStrikeData pastData = pastOiData.get(strike);

            if (pastData != null) {
                // Call Side
                if (currentData.getCallInfo() != null && pastData.getCallInfo() != null) {
                    double pastOiP = pastData.getCallInfo().getOi_change_percent();
                    // We simply store the PAST value, or we could calculate the difference here.
                    // The requirement says "Signal logic based on relative change of OI change
                    // percentages"
                    // So we probably want the past value so frontend can compute (current_change -
                    // past_change).
                    // Actually, let's store the past OI change percentage directly into the new
                    // field corresponding to time.

                    if (minutesAgo == 5) {
                        currentData.getCallInfo().setLast_5min_oi_change_percent(pastOiP);
                    } else if (minutesAgo == 15) {
                        currentData.getCallInfo().setLast_15min_oi_change_percent(pastOiP);
                    }
                }

                // Put Side
                if (currentData.getPutInfo() != null && pastData.getPutInfo() != null) {
                    double pastOiP = pastData.getPutInfo().getOi_change_percent();
                    if (minutesAgo == 5) {
                        currentData.getPutInfo().setLast_5min_oi_change_percent(pastOiP);
                    } else if (minutesAgo == 15) {
                        currentData.getPutInfo().setLast_15min_oi_change_percent(pastOiP);
                    }
                }
            }
        }
    }


    private void calculateResistanceSupport(MarketSnapshot marketSnapshot) {
        if (marketSnapshot == null || marketSnapshot.getBody() == null
                || marketSnapshot.getBody().getOiData() == null) {
            return;
        }

        java.util.Map<String, OiStrikeData> oiData = marketSnapshot.getBody().getOiData();

        for (java.util.Map.Entry<String, OiStrikeData> entry : oiData.entrySet()) {
            try {
                double strike = Double.parseDouble(entry.getKey());
                OiStrikeData data = entry.getValue();
                double callOi = data.getCallOi();
                double putOi = data.getPutOi();

                double pcr = 0.0;
                if (callOi > 0) {
                    pcr = putOi / callOi;
                    pcr = Math.round(pcr * 100.0) / 100.0;
                }

                // start calculation here
                int extremeResistanceStrike = 0;
                int extremeSupportStrike = 0;
                double result = (pcr - 1);

                if (result > 0) {
                    double resultMul = (result * 50);
                    // Avoid integer overflow or unrealistic values
                    if (resultMul > 10000)
                        resultMul = 10000;
                    extremeSupportStrike = (int) (strike + resultMul);
                    data.setExtremeSupport(extremeSupportStrike);
                    data.setExtremeResistance(0);
                } else {
                    double resultMul = (result * 50);
                    // Avoid integer overflow or unrealistic values
                    if (resultMul < -10000)
                        resultMul = -10000;
                    extremeResistanceStrike = (int) (strike + resultMul);
                    data.setExtremeSupport(0);
                    data.setExtremeResistance(extremeResistanceStrike);
                }
                // System.out.println(strike + " @@@ " + pcr + " @@@ " + extremeSupportStrike +
                // " @@@ " + extremeResistanceStrike);
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    private void calculateMaxPain(MarketSnapshot marketSnapshot) {
        if (marketSnapshot == null || marketSnapshot.getBody() == null
                || marketSnapshot.getBody().getOiData() == null) {
            return;
        }

        java.util.Map<String, OiStrikeData> oiData = marketSnapshot.getBody().getOiData();
        java.util.List<Double> strikes = new java.util.ArrayList<>();
        for (String strike : oiData.keySet()) {
            try {
                strikes.add(Double.parseDouble(strike));
            } catch (NumberFormatException e) {
                continue;
            }
        }

        double minTotalPain = Double.MAX_VALUE;
        double maxPainStrike = -1;

        for (Double potentialExpiry : strikes)
        {
            double totalPain = 0;
            for (java.util.Map.Entry<String, OiStrikeData> entry : oiData.entrySet()) {
                try {
                    double strike = Double.parseDouble(entry.getKey());
                    OiStrikeData data = entry.getValue();
                    long callOi = data.getCallOi();
                    long putOi = data.getPutOi();

                    double callPain = Math.max(0, potentialExpiry - strike) * callOi;
                    double putPain = Math.max(0, strike - potentialExpiry) * putOi;

                    totalPain += callPain + putPain;
                } catch (NumberFormatException e) {
                    System.out.println("Some issue here");
                    continue;
                }
            }

            if (totalPain < minTotalPain) {
                minTotalPain = totalPain;
                maxPainStrike = potentialExpiry;
            }
        }

        //System.out.println("Max Pain: " + maxPainStrike);
        marketSnapshot.setMaxPainStrike(maxPainStrike);
    }
}

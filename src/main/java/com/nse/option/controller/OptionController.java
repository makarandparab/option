package com.nse.option.controller;

import com.nse.option.model.callput.CallInfo;
import com.nse.option.model.callput.MarketSnapshot;
import com.nse.option.model.callput.OiStrikeData;
import com.nse.option.model.callput.PutInfo;
import com.nse.option.model.iv.ScreenerResponse;
import com.nse.option.service.ActiveContractCallService;
import com.nse.option.service.ActiveContractPutService;
import com.nse.option.service.MarketSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


@RestController
public class OptionController
{

    @Value("${app.allowed-strikes}")
    private List<Integer> allowedStrikes;

    @Autowired
    MarketSnapshotService marketSnapshotService;

    @Autowired
    ActiveContractPutService activeContractPutService;

    @Autowired
    ActiveContractCallService activeContractCallService;

    //public MarketSnapshot getOptionData()
    @GetMapping(value = "/optionData")
    public MarketSnapshot getOptionData()
    {
        String niftyValue = "";
        MarketSnapshot marketSnapshot = null;
        ScreenerResponse screenerResponsePut = null;
        ScreenerResponse screenerResponseCall = null;

        try
        {
            marketSnapshot = marketSnapshotService.getMarketSnapshot();

            // Filter strikePriceList
            double pcr = marketSnapshot.getBody().getOverallData().getTotalPCR();
            List<Integer> list = marketSnapshot.getBody().getOverallData().getStrikePriceList();
            list.retainAll(allowedStrikes);

            // Filter oiData
            marketSnapshot.getBody().getOiData().entrySet().removeIf(entry ->
                    !allowedStrikes.contains(Integer.parseInt(entry.getKey()))
            );

            //double spotPrice = marketSnapshot.getBody().getOverallData().getSpotPrice();
            //niftyValue = String.valueOf(spotPrice);

            //marketSnapshot.setNiftyData(niftyValue);
            //marketSnapshot.setNiftyValue(niftyValue + " - (" + String.format("%.2f", pcr) + ")");

            screenerResponsePut = activeContractPutService.getActiveContractPut();
            //System.out.println(screenerResponsePut.getHead().getStatus());

            screenerResponseCall = activeContractCallService.getActiveContractCall();
            //System.out.println(screenerResponseCall.getHead().getStatus());

            //Newly added code to update marketSnapshot object
            setPutValues(marketSnapshot, screenerResponsePut);
            setCallValues(marketSnapshot, screenerResponseCall);

            niftyValue = marketSnapshot.getSpotStrikePrice();
            System.out.println("@@@@ :::  " + niftyValue);
            marketSnapshot.setNiftyData(niftyValue);
            marketSnapshot.setNiftyValue(niftyValue + " - (" + String.format("%.2f", pcr) + ")");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return marketSnapshot;
    }


    private void setCallValues(MarketSnapshot marketSnapshot, ScreenerResponse screenerResponseCall)
    {
        if (screenerResponseCall == null || screenerResponseCall.getBody() == null)
            return;

        List<List<Object>> tableData  = screenerResponseCall.getBody().getTableData();
        for(Map.Entry<String, OiStrikeData> entry : marketSnapshot.getBody().getOiData().entrySet())
        {
            for (List<Object> row : tableData)
            {
                int strikePrice = toInt(row.get(2));
                int key = Integer.parseInt(entry.getKey());

                if( key == strikePrice )
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

        List<List<Object>> tableData  = screenerResponsePut.getBody().getTableData();
        for(Map.Entry<String, OiStrikeData> entry : marketSnapshot.getBody().getOiData().entrySet())
        {
            for (List<Object> row : tableData)
            {
                int strikePrice = toInt(row.get(2));
                int key = Integer.parseInt(entry.getKey());

                if( key == strikePrice )
                {
                    //System.out.println(entry.getKey() + " | " + strikePrice);
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

}


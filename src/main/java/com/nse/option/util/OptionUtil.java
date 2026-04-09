package com.nse.option.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OptionUtil
{
    public static List<Integer> getAllowedList(String niftyValue)
    {
        List<Integer> allowedStrikes = new ArrayList<Integer>();

        double niftyClose = Double.parseDouble(niftyValue);
        int interval = 50;
        int nearestStrike = (int) (Math.round(niftyClose / interval) * interval);

        //previous 5
        for (int i = 1; i <= 5; i++) {
            int strike = nearestStrike - (i * interval);
            allowedStrikes.add(Integer.valueOf(strike));
            //System.out.println("Previous " + i + " ::: " + strike);
        }

        // Next 5
        for (int i = 1; i <= 5; i++) {
            int strike = nearestStrike + (i * interval);
            allowedStrikes.add(Integer.valueOf(strike));

            if(i==1)
            {
                int keyStrike = strike - 50 ;
                allowedStrikes.add(Integer.valueOf(keyStrike));
            }

            //System.out.println("Next " + i + " ::: " + strike);
        }

        Collections.sort(allowedStrikes);
        //System.out.println(allowedStrikes);
        return allowedStrikes;
    }
}

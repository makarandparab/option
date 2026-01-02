package com.nse.option.model.callput;

public class PutInfo
{
    private String opt_type;
    private double strike_price;
    private double current_price;
    private double day_change_percent;
    private int volume; //volumes
    private double traded_contracts_change;
    private double total_traded_value;
    private double open_interest;
    private double open_interest_chg;
    private double oi_change_percent;
    private double implied_volatility;
    private double iv_change_percent;
    private double spot_strike_price;
    private double delta_calc;
    private double gamma_calc;
    private double rho_calc;
    private double theta_calc;
    private double vega_calc;
    private String get_built_up_str;
    private double last_5min_oi_change_percent;
    private double last_15min_oi_change_percent;

    public String getOpt_type() {
        return opt_type;
    }

    public void setOpt_type(String opt_type) {
        this.opt_type = opt_type;
    }

    public double getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(double strike_price) {
        this.strike_price = strike_price;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public double getDay_change_percent() {
        return day_change_percent;
    }

    public void setDay_change_percent(double day_change_percent) {
        this.day_change_percent = day_change_percent;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getTraded_contracts_change() {
        return traded_contracts_change;
    }

    public void setTraded_contracts_change(double traded_contracts_change) {
        this.traded_contracts_change = traded_contracts_change;
    }

    public double getTotal_traded_value() {
        return total_traded_value;
    }

    public void setTotal_traded_value(double total_traded_value) {
        this.total_traded_value = total_traded_value;
    }

    public double getOpen_interest() {
        return open_interest;
    }

    public void setOpen_interest(double open_interest) {
        this.open_interest = open_interest;
    }

    public double getOpen_interest_chg() {
        return open_interest_chg;
    }

    public void setOpen_interest_chg(double open_interest_chg) {
        this.open_interest_chg = open_interest_chg;
    }

    public double getOi_change_percent() {
        return oi_change_percent;
    }

    public void setOi_change_percent(double oi_change_percent) {
        this.oi_change_percent = oi_change_percent;
    }

    public double getImplied_volatility() {
        return implied_volatility;
    }

    public void setImplied_volatility(double implied_volatility) {
        this.implied_volatility = implied_volatility;
    }

    public double getIv_change_percent() {
        return iv_change_percent;
    }

    public void setIv_change_percent(double iv_change_percent) {
        this.iv_change_percent = iv_change_percent;
    }

    public double getSpot_strike_price() {
        return spot_strike_price;
    }

    public void setSpot_strike_price(double spot_strike_price) {
        this.spot_strike_price = spot_strike_price;
    }

    public double getDelta_calc() {
        return delta_calc;
    }

    public void setDelta_calc(double delta_calc) {
        this.delta_calc = delta_calc;
    }

    public double getGamma_calc() {
        return gamma_calc;
    }

    public void setGamma_calc(double gamma_calc) {
        this.gamma_calc = gamma_calc;
    }

    public double getRho_calc() {
        return rho_calc;
    }

    public void setRho_calc(double rho_calc) {
        this.rho_calc = rho_calc;
    }

    public double getTheta_calc() {
        return theta_calc;
    }

    public void setTheta_calc(double theta_calc) {
        this.theta_calc = theta_calc;
    }

    public double getVega_calc() {
        return vega_calc;
    }

    public void setVega_calc(double vega_calc) {
        this.vega_calc = vega_calc;
    }

    public String getGet_built_up_str() {
        return get_built_up_str;
    }

    public void setGet_built_up_str(String get_built_up_str) {
        this.get_built_up_str = get_built_up_str;
    }

    public double getLast_5min_oi_change_percent() {
        return last_5min_oi_change_percent;
    }

    public void setLast_5min_oi_change_percent(double last_5min_oi_change_percent) {
        this.last_5min_oi_change_percent = last_5min_oi_change_percent;
    }

    public double getLast_15min_oi_change_percent() {
        return last_15min_oi_change_percent;
    }

    public void setLast_15min_oi_change_percent(double last_15min_oi_change_percent) {
        this.last_15min_oi_change_percent = last_15min_oi_change_percent;
    }
}


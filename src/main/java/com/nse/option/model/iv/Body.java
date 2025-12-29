package com.nse.option.model.iv;

import java.util.List;

public class Body
{
    private String screenerType;
    private int limit;

    private List<TableHeader> tableHeaders;

    /**
     * Each row is an ordered list matching tableHeaders
     * First element is a StockCell, rest are primitive values
     */
    private List<List<Object>> tableData;

    private String sortBy;
    private String order;

    public String getScreenerType() {
        return screenerType;
    }

    public void setScreenerType(String screenerType) {
        this.screenerType = screenerType;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<TableHeader> getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(List<TableHeader> tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public List<List<Object>> getTableData() {
        return tableData;
    }

    public void setTableData(List<List<Object>> tableData) {
        this.tableData = tableData;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

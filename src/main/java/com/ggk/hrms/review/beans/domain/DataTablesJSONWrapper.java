package com.ggk.hrms.review.beans.domain;

import java.util.List;

public class DataTablesJSONWrapper {

    Long iTotalRecords;

    Long iTotalDisplayRecords;

    String sEcho;

    String sColumns;

    List aaData;

    public Long getiTotalRecords() {
	return iTotalRecords;
    }

    public void setiTotalRecords(Long iTotalRecords) {
	this.iTotalRecords = iTotalRecords;
    }

    public Long getiTotalDisplayRecords() {
	return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
	this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
	return sEcho;
    }

    public void setsEcho(String sEcho) {
	this.sEcho = sEcho;
    }

    public String getsColumns() {
	return sColumns;
    }

    public void setsColumns(String sColumns) {
	this.sColumns = sColumns;
    }

    public List getAaData() {
        return aaData;
    }

    public void setAaData(List aaData) {
        this.aaData = aaData;
    }

    
}

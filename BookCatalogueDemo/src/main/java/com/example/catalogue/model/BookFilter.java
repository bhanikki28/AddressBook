package com.example.catalogue.model;

public class BookFilter {
	
	public String filterType;
	public String filterValue;
	
	public BookFilter(String filterType, String filterValue) {
		super();
		this.filterType = filterType;
		this.filterValue = filterValue;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getFilterValue() {
		return filterValue;
	}
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

}

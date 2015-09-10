package com.ggk.hrms.review.ui.vo;

public class DropDownVO {
	
	private int id;
	private String value;
	
	public DropDownVO(){
		
	}
	
	public DropDownVO(int id,String value){
		this.id=id;
		this.value=value;
	}
	
	public DropDownVO(int id,int rating){
		this.id=id;
		this.value=Integer.toString(rating);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}

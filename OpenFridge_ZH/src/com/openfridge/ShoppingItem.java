package com.openfridge;

public class ShoppingItem {

	public ShoppingItem(String des, int i, int uid){
		description = des;
		id = i;
		userId = uid;
	}	
	
	public String getDescription() {
		return description;
	}
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}

	private String description;
    private int id;
    private int userId;
}

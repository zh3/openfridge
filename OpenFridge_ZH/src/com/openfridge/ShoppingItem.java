package com.openfridge;

public class ShoppingItem {
	private String description;
	private int id = -1;
	private int userId = -1;

	public ShoppingItem(String des) {
		this(des,"",DataClient.getInstance().getUID());
	}
	public ShoppingItem(String des, String i, String uid) {
		this(des, defaultInt(i), defaultInt(uid));		
	}
	private static int defaultInt(String i) {
		try {
			return Integer.parseInt(i.trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	public ShoppingItem(String des, int i, int uid) {
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

	public String toString() {
		return description;
	}
}

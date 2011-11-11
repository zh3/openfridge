package com.openfridge;

import java.net.URLDecoder;

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
	public ShoppingItem(String des, int i, int uid) {
		description = URLDecoder.decode(des);
		id = i;
		userId = uid;
	}

	private static int defaultInt(String i) {
		try {
			return Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return -1;
		}
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

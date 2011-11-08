package com.openfridge;

public class ShoppingItem {
	private String description;
	private int id;
	private int userId;

	public ShoppingItem(String des, String i, String uid) {
		description = des;
		try {
			id = Integer.parseInt(i);
		} catch (NumberFormatException e) {
		}
		try {
			userId = Integer.parseInt(uid);
		} catch (NumberFormatException e) {
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

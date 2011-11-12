package com.openfridge;

public class ShoppingItem extends DataObject {
	private static final long serialVersionUID = -6023850383266657691L;
	public ShoppingItem(String des) {
		super(des);
	}
	public ShoppingItem(String des, String id, String userId) {
		super(des, id, userId);
	}
	public ShoppingItem(String des, int id, int uid) {
		super(des, id, uid);
	}
}

package com.openfridge;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import android.util.Log;

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

	@Override
	public void push() throws IOException {
		URL url = new URL(String.format(
				"http://openfridge.heroku.com/shopping_lists/push/%d/%s",
				getUserId(), URLEncoder.encode(getDescription(), "UTF-8")));
		
		Scanner scan = new Scanner((new DataInputStream(url.openStream())));

		int id = scan.nextInt();

		Log.d("OpenFridge",String.format("shopping ID: %d",id));
		setId(id);
		DataClient.getInstance().getShoppingList().add(this);
	}

	@Override
	public void remove() throws IOException {
		URL url = new URL(String.format(
				"http://openfridge.heroku.com/shopping_lists/destroy/%d",
				getId()));
		url.openStream().read();
		DataClient.getInstance().getShoppingList().remove(this);
	}
}

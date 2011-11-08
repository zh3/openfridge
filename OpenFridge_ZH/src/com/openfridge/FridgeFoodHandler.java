package com.openfridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.openfridge.TagStateEnum.FridgeFoodTagState;

public class FridgeFoodHandler extends SAXHandler<FridgeFoodTagState> {
	{
		buffers = new EnumMap<FridgeFoodTagState, StringBuilder>(
				FridgeFoodTagState.class);
		values = Arrays.asList(FridgeFoodTagState.values());
	}
	private ExpireState expirationState;

	private enum ExpireState {
		NEAR, GOOD, EXPIRED
	}

	private Map<ExpireState, ArrayList<FridgeFood>> foodLists = new EnumMap<ExpireState, ArrayList<FridgeFood>>(
			ExpireState.class);

	private void logNumber(ExpireState e) {
		Log.d("OpenFridge", String.format("Number of %s Foods: %d\n", e,
				foodLists.get(e).size()));
	}

	public ArrayList<FridgeFood> getGoodFoods() {
		logNumber(ExpireState.GOOD);
		return foodLists.get(ExpireState.GOOD);
	}

	public ArrayList<FridgeFood> getNearFoods() {
		logNumber(ExpireState.NEAR);
		return foodLists.get(ExpireState.NEAR);
	}

	public ArrayList<FridgeFood> getExpiredFoods() {
		logNumber(ExpireState.EXPIRED);
		return foodLists.get(ExpireState.EXPIRED);
	}

	@Override
	public void startDocument() throws SAXException {
		expirationState = null;
		for (ExpireState key : ExpireState.values()) {
			foodLists.put(key, new ArrayList<FridgeFood>());
		}
		super.startDocument();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		super.startElement(namespaceURI, localName, qName, atts);
		try {
			expirationState = ExpireState.valueOf(normalize(localName));
		} catch (IllegalArgumentException e) {
		}
		// Log.d("OpenFridge", String.format("expireState%s\n",
		// expirationState));
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("food")) {
			FridgeFood newFood = new FridgeFood(
					getString(FridgeFoodTagState.DESC),
					getString(FridgeFoodTagState.EXPIRATION),
					getString(FridgeFoodTagState.CREATED_AT),
					getString(FridgeFoodTagState.UPDATED_AT),
					getString(FridgeFoodTagState.ID),
					getString(FridgeFoodTagState.USER_ID));

			foodLists.get(expirationState).add(newFood);

			clearTagBuffers();
		}
	}

	@Override
	protected FridgeFoodTagState valueOf(String s) {
		return FridgeFoodTagState.valueOf(normalize(s));
	}
}
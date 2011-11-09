package com.openfridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
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
	private ExpState expState;

	private Map<ExpState, ArrayList<FridgeFood>> foodLists = new EnumMap<ExpState, ArrayList<FridgeFood>>(
			ExpState.class);
	{
		for (ExpState key : ExpState.values()) {
			foodLists.put(key, new ArrayList<FridgeFood>());
		}
	}

	
	@Override
	public void startDocument() throws SAXException {
		expState = null;
		super.startDocument();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		super.startElement(namespaceURI, localName, qName, atts);
		try {
			expState = ExpState.valueOf(normalize(localName));
		} catch (IllegalArgumentException e) {
		}
		// Log.d("OpenFridge", String.format("expireState%s\n",
		// expState));
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

			foodLists.get(expState).add(newFood);

			clearTagBuffers();
		}
	}

	@Override
	protected FridgeFoodTagState valueOf(String s) {
		return FridgeFoodTagState.valueOf(normalize(s));
	}

	public List<FridgeFood> getFoods(ExpState key) {
		Log.d("OpenFridge", String.format("Number of %s Foods: %d\n", key,
				foodLists.get(key).size()));
		return foodLists.get(key);
	}
}
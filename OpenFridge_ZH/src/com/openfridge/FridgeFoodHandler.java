package com.openfridge;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class FridgeFoodHandler extends DefaultHandler {

	private TagState tagState;
	private ExpireState expirationState;

	private enum ExpireState {
		NO_EXPIRATION_STATE, NEAR, GOOD, EXPIRED
	}

	private enum TagState {
		NO_TAG, CREATED_AT, DESC, EXPIRATION, ID, UPDATED_AT, USER_ID
	}

	private Map<TagState, StringBuilder> buffers = new EnumMap<TagState, StringBuilder>(
			TagState.class);
	private Map<ExpireState, ArrayList<FridgeFood>> foodLists = new EnumMap<ExpireState, ArrayList<FridgeFood>>(
			ExpireState.class);

	// ===========================================================
	// Getters
	// ===========================================================

	public ArrayList<FridgeFood> getGoodFoods() {
		System.err.format("Number of Good Foods: %d\n", foodLists.get(ExpireState.GOOD).size());
		return foodLists.get(ExpireState.GOOD);
	}

	public ArrayList<FridgeFood> getNearFoods() {
		System.err.format("Number of Near Foods: %d\n", foodLists.get(ExpireState.NEAR).size());
		return foodLists.get(ExpireState.NEAR);
	}

	public ArrayList<FridgeFood> getExpiredFoods() {
		System.err.format("Number of Expired Foods: %d\n", foodLists.get(ExpireState.EXPIRED).size());
		return foodLists.get(ExpireState.EXPIRED);
	}

	// ===========================================================
	// Overridden Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		expirationState = ExpireState.NO_EXPIRATION_STATE;
		for (ExpireState key : ExpireState.values()) {
			foodLists.put(key, new ArrayList<FridgeFood>());
		}		
		clearTagBuffers();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		String normalizedLocalName = localName.replace('-', '_').toUpperCase();
		try {
			tagState = TagState.valueOf(normalizedLocalName);
		} catch (IllegalArgumentException e) {}
		try {
			expirationState = ExpireState.valueOf(normalizedLocalName);
		} catch (IllegalArgumentException e) {}
		Log.d("Openfridge", String.format("start: localName:%s\n tagState:%s\n expireState%s\n", localName, tagState, expirationState));
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("food")) {
			FridgeFood newFood = new FridgeFood(
					getString(TagState.DESC),
					getString(TagState.EXPIRATION),
					getString(TagState.CREATED_AT),
					getString(TagState.UPDATED_AT),
					getString(TagState.ID),
					getString(TagState.USER_ID));

			foodLists.get(expirationState).add(newFood);

			clearTagBuffers();
		}
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		buffers.get(tagState).append(new String(ch, start, length));
	}

	// ===========================================================
	// Helper Methods
	// ===========================================================

	private void clearTagBuffers() {
		tagState = TagState.NO_TAG;
		for (TagState key : TagState.values()) {
			buffers.put(key, new StringBuilder());
		}
	}

	private String getString(final TagState key) {
		return buffers.get(key).toString();
	}

}
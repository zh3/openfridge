package com.openfridge;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ShoppingItemHandler extends DefaultHandler {

	private TagState tagState;

	private enum TagState {
		NO_TAG, CREATED_AT, DESC, ID, UPDATED_AT, USER_ID
	}

	private Map<TagState, StringBuilder> buffers = new EnumMap<TagState, StringBuilder>(
			TagState.class);
	private ArrayList<ShoppingItem> foodList = new ArrayList<ShoppingItem>();

	// ===========================================================
	// Getters
	// ===========================================================

	public ArrayList<ShoppingItem> getFoods() {
		return foodList;
	}

	// ===========================================================
	// Overridden Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
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
		//Log.d("Openfridge", String.format("start: localName:%s\n tagState:%s\n", localName, tagState));
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("shopping-list")) { //TODO change this element to "shopping-item", for sanity's sake.
			ShoppingItem newFood = new ShoppingItem(
					getString(TagState.DESC),
//					getString(TagState.CREATED_AT),
//					getString(TagState.UPDATED_AT),
					getString(TagState.ID),
					getString(TagState.USER_ID));

			foodList.add(newFood);

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
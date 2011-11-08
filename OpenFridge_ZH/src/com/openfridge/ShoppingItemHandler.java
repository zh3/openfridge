package com.openfridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

import org.xml.sax.SAXException;

import com.openfridge.TagStateEnum.ShoppingItemTagState;

public class ShoppingItemHandler extends Handler<ShoppingItemTagState> {

	{
		buffers = new EnumMap<ShoppingItemTagState, StringBuilder>(
				ShoppingItemTagState.class);
		values = Arrays.asList(ShoppingItemTagState.values());
	}
	private ArrayList<ShoppingItem> foodList = new ArrayList<ShoppingItem>();

	public ArrayList<ShoppingItem> getFoods() {
		return foodList;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("shopping-list")) { // TODO change this element to
													// "shopping-item", for
													// sanity's sake.
			ShoppingItem newFood = new ShoppingItem(
					getString(ShoppingItemTagState.DESC),
					// getString(ShoppingItemTagState.CREATED_AT),
					// getString(ShoppingItemTagState.UPDATED_AT),
					getString(ShoppingItemTagState.ID),
					getString(ShoppingItemTagState.USER_ID));

			foodList.add(newFood);

			clearTagBuffers();
		}
	}

	@Override
	protected ShoppingItemTagState valueOf(String s) {
		return ShoppingItemTagState.valueOf(normalize(s));
	}

}
package com.openfridge;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FridgeFoodHandler extends DefaultHandler
{
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ArrayList<FridgeFood> getParsedData() {
        return this.foods;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
        foods = new ArrayList<FridgeFood>();
        parseState = FridgeFoodTagParseState.NO_TAG;
        clearBuffers();
    }

    @Override
    public void endDocument() throws SAXException {
        // Nothing to do
    }
    
    private void clearBuffers() {
        createdDateTime = "";
        description = "";
        expirationDate = "";
        id = "";
        lastUpdatedDateTime = "";
        userId = "";
    }

    /** Gets be called on opening tags like: 
     * <tag> 
     * Can provide attribute(s), when xml was like:
     * <tag attribute="attributeValue">*/
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (localName.equals("created-at")) {
            parseState = FridgeFoodTagParseState.CREATE_TIME;
        } else if (localName.equals("desc")) {
            parseState = FridgeFoodTagParseState.DESCRIPTION;
        } else if (localName.equals("expiration")) {
            parseState = FridgeFoodTagParseState.EXPIRATION;
        } else if (localName.equals("id")) {
            parseState = FridgeFoodTagParseState.ID;
        } else if (localName.equals("updated-at")) {
            parseState = FridgeFoodTagParseState.LAST_UPDATE_TIME;
        } else if (localName.equals("user-id")) {
            parseState = FridgeFoodTagParseState.USER_ID;
        }
    }
    
    /** Gets be called on closing tags like: 
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals("fridge-food")) {
            FridgeFood newFood = new FridgeFood(createdDateTime, description, 
                    expirationDate, id, lastUpdatedDateTime, userId);
            this.foods.add(newFood);
            parseState = FridgeFoodTagParseState.NO_TAG;
            clearBuffers();
        }
    }
    
    /** Gets be called on the following structure: 
     * <tag>characters</tag> */
    @Override
    public void characters(char ch[], int start, int length) {
        switch (parseState) {
        case CREATE_TIME:
            createdDateTime += new String(ch, start, length);
            break;
        case DESCRIPTION:
            description += new String(ch, start, length);
            break;
        case EXPIRATION:
            expirationDate += new String(ch, start, length);
            break;
        case ID:
            id += new String(ch, start, length);
            break;
        case LAST_UPDATE_TIME:
            lastUpdatedDateTime += new String(ch, start, length);
            break;
        case USER_ID:
            userId += new String(ch, start, length);
            break;
        default:
        }
    }
    
    private String createdDateTime;
    private String description;
    private String expirationDate;
    private String id;
    private String lastUpdatedDateTime;
    private String userId;
    
    private ArrayList<FridgeFood> foods;
    private FridgeFoodTagParseState parseState;
}
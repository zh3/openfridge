package com.photoshimona.openfridge;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandler extends DefaultHandler
{
    // ===========================================================
    // Fields
    // ===========================================================
    
    private boolean in_user = false;
    private boolean in_uname = false;
    private String myString = "oh yeah";
    


    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public String getParsedData() {
        return this.myString;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
        this.myString = "start";
    }

    @Override
    public void endDocument() throws SAXException {
        // Nothing to do
    }

    /** Gets be called on opening tags like: 
     * <tag> 
     * Can provide attribute(s), when xml was like:
     * <tag attribute="attributeValue">*/
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (localName.equals("user")) {
            this.in_user = true;
        }else if (localName.equals("uname")) {
            this.in_uname = true;
        }
    }
    
    /** Gets be called on closing tags like: 
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals("user")) {
            this.in_user = false;
        }else if (localName.equals("uname")) {
            this.in_uname = false;
        }
    }
    
    /** Gets be called on the following structure: 
     * <tag>characters</tag> */
    @Override
    public void characters(char ch[], int start, int length) {
        if(this.in_uname){
            this.myString = new String(ch, start, length);
        }
    }
}
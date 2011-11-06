package com.openfridge;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Encapsulates the data associated with an item of food, including description
 * and expiry date.
 * 
 * @author Tom
 *
 */
public class FridgeFood implements Cloneable {
    
    public FridgeFood(String descriptionString, String expirationDateString,
                      String userIdString) {
        description = descriptionString.trim();
        userId = Integer.parseInt(userIdString.trim());
        expirationDate = parseSimpleDateTime(expirationDateString);
        lastUpdateDateTime = null;
        creationDateTime = null;
    }
    
    public FridgeFood(String creationDateTimeString, String descriptionString, 
            String expirationDateString, String idString, 
            String lastUpdateDateTimeString, String userIdString) {
        idString = idString.trim();
        userIdString = userIdString.trim();
        
        creationDateTime = parseXMLDateTime(creationDateTimeString);
        description = descriptionString.trim();
        expirationDate = parseXMLDateTime(expirationDateString);
        id = Integer.parseInt(idString);
        lastUpdateDateTime = parseXMLDateTime(lastUpdateDateTimeString);
        userId = Integer.parseInt(userIdString);
    }
    
    /**
     * Parse a string containing date and time information from the XML format
     *  to a GregorianCalendar object representation
     * 
     * @param xmlDateTime an XML string with Date and Time information in the
     * format yyyy-mm-ddThh:mm:ssZ.
     * @return An object representation of the given Date and Time string
     */
    public static GregorianCalendar parseXMLDateTime(String xmlDateTime) {
        String dateTimeString = getSpaceDelimitedDateTime(xmlDateTime);
        Scanner s = new Scanner(dateTimeString);
        
        int year = s.nextInt();
        int month = s.nextInt();
        int day = s.nextInt();
        
        // Return if only the date is included
        if (!s.hasNext()) return new GregorianCalendar(year, month, day);
        int hour   = s.nextInt();
        int minute = s.nextInt();
        int second = s.nextInt();
        
        return new GregorianCalendar(year, month, day, hour, minute, second);
    }
    
    /**
     * Parse a simple date time string into a GregorianCalendar object
     * 
     * @param dateTime A date time string in the form YYYY-MM-DD
     * @return a GregorianCalendar object representing the given string
     */
    public static GregorianCalendar parseSimpleDateTime(String dateTime) {
        String spacedDateTime = getSpaceDelimitedDateTime(dateTime);
        Scanner s = new Scanner(spacedDateTime);
        
        int year = s.nextInt();
        int month = s.nextInt();
        int day = s.nextInt();
        
        return new GregorianCalendar(year, month, day);
    }
    
    private static String getSpaceDelimitedDateTime(String xmlDateTime) {
        String dateTime = xmlDateTime.replace('-', ' ');
        dateTime = dateTime.replace('T', ' ');
        dateTime = dateTime.replace('Z', ' ');
        dateTime = dateTime.replace(':', ' ');
        
        return dateTime;
    }
    
    public GregorianCalendar getCreationDateTime() {
        return (GregorianCalendar) creationDateTime.clone();
    }
    
    public String getCreationDateTimeString() {
        return creationDateTime.toString();
    }
    
    public String getDescription() {
        return description;
    }
    
    public GregorianCalendar getExpirationDate() {
        return (GregorianCalendar) expirationDate.clone();
    }
    
    public String getExpirationDateString() {
        return expirationDate.get(Calendar.YEAR) + "-" 
                + expirationDate.get(Calendar.MONTH) + "-" 
                + expirationDate.get(Calendar.DAY_OF_MONTH);
    }
    
    public int getExpirationYear() {
        return expirationDate.get(Calendar.YEAR);
    }
    
    public int getExpirationMonth() {
        return expirationDate.get(Calendar.MONTH);
    }
    
    public int getExpirationDay() {
        return expirationDate.get(Calendar.DAY_OF_MONTH);
    }
    
    public int getId() {
        return id;
    }
    
    public GregorianCalendar getLastUpdateDateTime() {
        return (GregorianCalendar) lastUpdateDateTime.clone();
    }
    
    public String getLastUpdateDateTimeString() {
        return lastUpdateDateTime.toString();
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String toString() {
        return description;
    }
    
    /**
     * Get a short string summary of this food item, including its description
     * and expiration date.
     * 
     * @return A string containing a summary of this food record
     */
    public String getSummary() {
        return description + "\nExpires: " + expirationDate.get(Calendar.YEAR)
               + "-" + expirationDate.get(Calendar.MONTH) + "-" 
               + expirationDate.get(Calendar.DAY_OF_MONTH)+ "\n";
    }
    
    public Object clone() {
        try {
            FridgeFood o = (FridgeFood) super.clone();
            o.creationDateTime = null;
            o.lastUpdateDateTime = null;
            
            if (creationDateTime != null) {
                o.creationDateTime 
                    = (GregorianCalendar) creationDateTime.clone();
            }
            
            if (lastUpdateDateTime != null) {
                o.lastUpdateDateTime 
                    = (GregorianCalendar) lastUpdateDateTime.clone();
            }
            
            o.expirationDate = (GregorianCalendar) expirationDate.clone();
            
            return o;
        } catch (CloneNotSupportedException e) {
            // Should never happen
            return null;
        }
    }
		
    private GregorianCalendar creationDateTime;
    private String description;
    private GregorianCalendar expirationDate;
    private int id;
    private GregorianCalendar lastUpdateDateTime;
    private int userId;
}
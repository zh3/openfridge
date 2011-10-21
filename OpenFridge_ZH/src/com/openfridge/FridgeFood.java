package com.openfridge;

import java.util.GregorianCalendar;
import java.util.Scanner;

public class FridgeFood {
    
    public FridgeFood(String creationDateTimeString, String descriptionString, 
            String expirationDateString, String idString, 
            String lastUpdateDateTimeString, String userIdString) {
        creationDateTime = parseXMLDateTime(creationDateTimeString);
        description = descriptionString;
        expirationDate = parseXMLDateTime(expirationDateString);
        id = Integer.parseInt(idString);
        lastUpdateDateTime = parseXMLDateTime(lastUpdateDateTimeString);
        userId = Integer.parseInt(userIdString);
    }
    
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
    
    private static String getSpaceDelimitedDateTime(String xmlDateTime) {
        String dateTime = xmlDateTime.replace('-', ' ');
        dateTime = dateTime.replace('T', ' ');
        dateTime = dateTime.replace('Z', ' ');
        
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
        return expirationDate.toString();
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
		
    private GregorianCalendar creationDateTime;
    private String description;
    private GregorianCalendar expirationDate;
    private int id;
    private GregorianCalendar lastUpdateDateTime;
    private int userId;
}

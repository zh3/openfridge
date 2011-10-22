package com.openfridge;

public enum FridgeFoodTagParseState {
    NO_TAG,
    CREATE_TIME,
    DESCRIPTION,
    EXPIRATION,
    ID,
    LAST_UPDATE_TIME,
    USER_ID
}

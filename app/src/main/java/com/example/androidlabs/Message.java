package com.example.androidlabs;

public class Message {
    private boolean theSide;
    private String theMessage;

    public Message(String theMessage, boolean theSide)
    {
        this.theSide = theSide;
        this.theMessage = theMessage;
    }

    public boolean isSide(){
        return theSide;
    }

    public String getMessage(){
        return theMessage;
    }
}

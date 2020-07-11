package com.example.androidlabs;

public class Message
{
    private String message;
    private boolean side;
    private long id;

    public Message(String message, boolean side)
    {
        this.message = message;
        this.side = side;
    }

    public Message(String message, boolean side, long id)
    {
        this.message = message;
        this.side = side;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSide() {
        return side;
    }

    public long getId() {
        return id;
    }
}
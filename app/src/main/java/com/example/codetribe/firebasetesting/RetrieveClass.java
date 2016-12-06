package com.example.codetribe.firebasetesting;

/**
 * Created by Codetribe on 2016/11/21.
 */

public class RetrieveClass {
    private String Uid;
    private String email;
    private String message;

    public RetrieveClass(String defaultUID, String defaultEmail, String defaultMessage) {
        defaultUID = Uid;
        defaultEmail = email;
        defaultMessage = message;
    }

    public String getDefaultUID()
    {
        return Uid;
    }
    public String getDefaultEmail()
    {
        return email;
    }
    public String getDefaultMessage()
    {
        return message;
    }
}
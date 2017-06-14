package com.twln;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Theodore Newton on 6/11/2017.
 */
public class Account {
    String uID, uName, uNick;

    public Account(String uID, String uName, String uNick){
        this.uID = uID;
        this.uName = uName;
        this.uNick = uNick;
    }

    public String toString(){
        String out = this.uID + " " + this.uName + " " + this.uNick;
        return out;
    }
}

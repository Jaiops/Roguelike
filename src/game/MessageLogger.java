package game;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Johan on 2016-07-14.
 */
public class MessageLogger  {
    ArrayList<String> log = new ArrayList<>();


    private static MessageLogger instance;
    int messageMaxLength = 40;

    private MessageLogger() {


    }

    public static MessageLogger getInstance() {
        if(instance == null){
            instance = new MessageLogger();
        }
        return instance;
    }

    public void logMessage(String msg) {
        if (msg.length() <= messageMaxLength) {
            log.add(msg);
        } else {
            String temp = msg.substring(0, messageMaxLength);
            msg = msg.substring(messageMaxLength);
            logMessage(temp);
            logMessage(msg);
        }
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void dumpToErr() {
        for (String s : log) {
            System.err.println(s);
        }
    }
}

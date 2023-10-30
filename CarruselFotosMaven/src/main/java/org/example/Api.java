package org.example;

import java.util.ArrayList;

public class Api {
    public ArrayList<String> message;
    public String status;

    public Api(ArrayList<String> message, String status) {
        this.message = message;
        this.status = status;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

}
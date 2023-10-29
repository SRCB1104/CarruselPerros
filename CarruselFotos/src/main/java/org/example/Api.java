package org.example;

import java.util.ArrayList;

public class Api {
    public ArrayList<String> mensaje;
    public String status;

    public Api(ArrayList<String> message, String status) {
        this.mensaje = message;
        this.status = status;
    }

    public ArrayList<String> getMessage() {
        return mensaje;
    }

}

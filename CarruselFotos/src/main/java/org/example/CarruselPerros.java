package org.example;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CarruselPerros {
    public static final String BASE_URL = "https://dog.ceo/api/breeds/image/random/";
    private static int currentIndex = 0;
    private static Timer timer;
    private static JFrame frame;
    private static ArrayList<String> dogUrls;
    private static String raza = "";

    public static void main(String[] args) {
        double valorRandom = 10 + (Math.random() * 11);
        int n = (int) valorRandom;
        if (args.length != 0) {
            n = Integer.parseInt(args[0]);
        }

        raza = JOptionPane.showInputDialog("Ingrese una raza de perro :");

        Api response = query(n);
        dogUrls = response.getMessage();

        if (dogUrls.isEmpty()) {
            System.out.println("No se encontraron imagenes de esa raza");
            return;
        }

        frame = new JFrame("Carrusel de fotos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        siguienteImagen();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                siguienteImagen();
            }
        }, 15000, 15000);
    }

    public static Api query(int n) {
        Api response = null;
        try {
            URL u;
            if (raza.isEmpty()) {
                u = new URL(BASE_URL + n);
            } else {
                u = new URL("https://dog.ceo/api/breed/" + raza.toLowerCase() + "/images/random/" + n);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
            String buffer;
            StringBuilder jsonText = new StringBuilder();
            while ((buffer = br.readLine()) != null) {
                jsonText.append(buffer);
            }
            br.close();
            Gson gson = new Gson();
            response = gson.fromJson(jsonText.toString(), Api.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private static void siguienteImagen() {
        if (dogUrls.isEmpty()) {
            return;
        }

        if (currentIndex >= dogUrls.size()) {
            currentIndex = 0;
        }

        String imageUrl = dogUrls.get(currentIndex);
        try {
            URL imgURL = new URL(imageUrl);
            EventQueue.invokeLater(() -> {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new Panel(imgURL));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });

            currentIndex++;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
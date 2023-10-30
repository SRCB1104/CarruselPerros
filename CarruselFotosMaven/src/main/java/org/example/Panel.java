package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Panel extends JPanel {
    private ImageIcon icon;
    private MediaTracker tracker;
    private BufferedImage image;

    public Panel(URL imgUrl) {
        super();
        tracker = new MediaTracker(this);
        loadImage(imgUrl);
        initPanel();
    }

    private void loadImage(URL imgUrl) {
        try {
            image = ImageIO.read(imgUrl);
            tracker.addImage(image, 1);
            icon = new ImageIcon(image);
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }



    private void initPanel(){
        try{
            tracker.waitForID(1);

        } catch (InterruptedException e) {
        }
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        setPreferredSize(new Dimension(w,h));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if((tracker.statusAll(false) & MediaTracker.ERRORED) != 0){
            g.fillRect(0,0,size().width, size().height);
            return;

        }
        g.drawImage(image, 0,0,this);

    }

}
package lolz.Hub;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Hub {
    private Image image;
    private BufferedImage map;
    public Hub(){
        try {
            image = ImageIO.read(new File("res/Skizze Hub.jpg"));
            map = ImageIO.read(new File("res/hub/Forest Tileset.png"));
        } catch (Exception e){
            System.out.println("Fehler in lolz.Hub");
        }

    }
    public void paint(Graphics g){
        g.drawImage(image, 0, 0, 960, 540, null);

    }

}

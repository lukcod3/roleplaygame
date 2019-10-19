package lolz.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Gold {
    public int amount;
    public Image[] images;
    public double animation_state;

    public Gold() {
        amount = 0;
        animation_state = 0;
        images = new Image[5];
        try {
            for (int i = 0; i <= 4; i++) {
                images[i] = ImageIO.read(new File("res/Individual Sprites/MonedaD.png")).getSubimage(i * 16, 0, 16, 16).getScaledInstance(30, -1, Image.SCALE_DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.white);
        g.drawString(""+ amount, 20, 130);
        g.drawImage(images[(int)(animation_state%5)], 50, 107 ,null);
        g.setColor(color);
    }

    public void update(){
        animation_state += 0.1;
    }
}

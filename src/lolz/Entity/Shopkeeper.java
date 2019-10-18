package lolz.Entity;

import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Shopkeeper {
    public Image[] images;
    public double animation_state;

    public Shopkeeper(){
        images = new Image[4];
        try{
            for(int i = 0; i<= 3; i++){
                images[i] = ImageIO.read(new File("res/monster/Phantom Knight/Individual Sprites/phantom knight-idle-0" + i + ".png")).getScaledInstance(100, -1, Image.SCALE_DEFAULT);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        animation_state = 0;
    }
    public void update(){
        animation_state += 0.1;
    }
    public void paint(Graphics g){
        Main.drawReflectImage(images[(int) (animation_state)%4], g, 60, 330);
    }
}

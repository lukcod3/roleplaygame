package lolz.Entity;

import lolz.GUI.Item;
import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Shopkeeper {
    public Image[] images;
    public double animation_state;
    public boolean showShop;
    public Player player;
    int X_POSITION_IMAGE_SHOP = 560;
    int Y_POSITION_IMAGE_SHOP = 185;
    public Item[] shop;

    public Shopkeeper(Player player){
        images = new Image[4];
        try{
            for(int i = 0; i<= 3; i++){
                images[i] = ImageIO.read(new File("res/monster/Phantom Knight/Individual Sprites/phantom knight-idle-0" + i + ".png")).getScaledInstance(100, -1, Image.SCALE_DEFAULT);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        animation_state = 0;
        showShop = false;
        this.player = player;
        getShopItems();
    }
    public void update(){
        animation_state += 0.1;
        animation_state %= 4;
    }
    public void paint(Graphics g){
        Main.drawReflectImage(images[(int) (animation_state)], g, 60, 330);
    }
    // called in hub
    public void printShop(Graphics g){
        if(showShop) {
            Color myColor = new Color(56, 56, 56, 230);
            Font titleF = new Font("SansSerif", Font.BOLD, 25);
            g.drawRect(150, 50, 660, 440);
            g.setColor(myColor);
            g.fillRect(150, 50, 660, 440);
            g.setFont(titleF);
            g.setColor(Color.white);
            g.drawString("Shop", 260, 80);
            g.drawString("Inventar", 570, 80);

            // draw player
            if (this.player instanceof Fighter) {
                if (this.player.turnedRight) {
                    if (this.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        g.drawImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_SHOP, Y_POSITION_IMAGE_SHOP, null); // set player's animation to hit animation
                    } else if (this.player.isMoving) {
                        g.drawImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_SHOP, Y_POSITION_IMAGE_SHOP, null);
                    } else {
                        g.drawImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_SHOP, Y_POSITION_IMAGE_SHOP, null);
                    }
                } else {
                    if (this.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        Main.drawReflectImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_SHOP, Y_POSITION_IMAGE_SHOP);
                    } else if (this.player.isMoving) {
                        Main.drawReflectImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_SHOP, Y_POSITION_IMAGE_SHOP);
                    } else {
                        Main.drawReflectImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_SHOP, Y_POSITION_IMAGE_SHOP);
                    }
                }
            } else {
                if (!this.player.turnedRight) {
                    if (this.player.isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        int offset = (int) ((1.6 * this.player.img[2][(int) this.player.animation_state % 5].getWidth(null) - this.player.width) / 2);
                        g.drawImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_SHOP - offset, Y_POSITION_IMAGE_SHOP, null); // set player's animation to hit animation
                    } else if (this.player.isMoving) {
                        int offset = (int) ((1.6 * this.player.img[1][(int) this.player.animation_state % 6].getWidth(null) - this.player.width) / 2);
                        g.drawImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_SHOP - offset, Y_POSITION_IMAGE_SHOP, null);
                    } else {
                        int offset = (int) ((1.6 * this.player.img[0][(int) this.player.animation_state % 4].getWidth(null) - this.player.width) / 2);
                        g.drawImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_SHOP - offset, Y_POSITION_IMAGE_SHOP, null);
                    }
                } else {
                    if (this.player.isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        int offset = (int) ((1.6 * this.player.img[2][(int) this.player.animation_state % 5].getWidth(null) - this.player.width) / 2);
                        Main.drawReflectImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_SHOP + offset, Y_POSITION_IMAGE_SHOP);
                    } else if (this.player.isMoving) {
                        int offset = (int) ((1.6 * this.player.img[1][(int) this.player.animation_state % 6].getWidth(null) - this.player.width) / 2);
                        Main.drawReflectImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_SHOP + offset, Y_POSITION_IMAGE_SHOP);
                    } else {
                        int offset = (int) ((1.6 * this.player.img[0][(int) this.player.animation_state % 4].getWidth(null) - this.player.width) / 2);
                        Main.drawReflectImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_SHOP + offset, Y_POSITION_IMAGE_SHOP);
                    }
                }
            }
            // draw inventory images
            g.drawImage(this.player.inventory.equipment[0].image, 590, 100, null);
            g.drawImage(this.player.inventory.equipment[1].image, 500, 180, null);
            g.drawImage(this.player.inventory.equipment[2].image, 500, 255, null);
            g.drawImage(this.player.inventory.equipment[3].image, 590, 330, null);
            g.drawImage(this.player.inventory.equipment[4].image, 680, 155, null);
            g.drawImage(this.player.inventory.equipment[5].image, 680, 230, null);
            g.drawImage(this.player.inventory.equipment[6].image, 680, 305, null);
            for (int i = 0; i <= 3; i++) {
                Image a;
                if (this.player.inventory.equipment[i + 7] == null) {
                    a = player.inventory.emptyInventory;
                } else {
                    a = this.player.inventory.equipment[i + 7].image;
                }
                g.drawImage(a, 500 + (i * 61), 420, null);
            }

            // draw Shopkeeper
            Main.drawReflectImage(images[(int) animation_state].getScaledInstance(140, -1, Image.SCALE_DEFAULT), g, 235, 130);

            // draw Shopitems
            for(int i = 0; i<= 1; i++){
                for(int j = 0; j<= 2; j++){
                    g.drawImage(shop[3*i + j].image, 200 + 61* j, 300 + 61 * i, null);
                }
            }
        }
    }
    public void getShopItems(){
        shop = new Item[6];
        for(int i = 0; i<= 5; i++){
            int stufe;

            if((int)(1+Math.random()*10)*this.player.level > 60){
                stufe = 4;
            } else if ((int)(1+Math.random()*10)*this.player.level > 40){
                stufe = 3;
            } else if ((int)(1+Math.random()*10)*this.player.level > 15){
                stufe = 2;
            } else{
                stufe = 1;
            }
            shop[i] = this.player.inventory.item[(int) (Math.random()*7)][stufe];
        }
    }
}

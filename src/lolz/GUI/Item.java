package lolz.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Item {
    public int health, damage, abilitypower, armor;
    public int typ, itemNr; //schwert, hut etc. (0-6);  stufe(0-4)
    public double movementspeed;
    public Image image;

    public Item(int typ, int itemNr) {
        this.typ = typ;
        this.itemNr = itemNr;
        generateItemStats();
        try {
            switch (typ){
                case 0: image = ImageIO.read(new File("res/inventory/hut" + itemNr + ".png")); break;
                case 1: image = ImageIO.read(new File("res/inventory/ruestung" + itemNr + ".png")); break;
                case 2: image = ImageIO.read(new File("res/inventory/schwert" + itemNr + ".png")); break;
                case 3: image = ImageIO.read(new File("res/inventory/schuhe" + itemNr + ".png")); break;
                case 4: image = ImageIO.read(new File("res/inventory/kette" + itemNr + ".png")); break;
                case 5: image = ImageIO.read(new File("res/inventory/ring" + itemNr + ".png")); break;
                case 6: image = ImageIO.read(new File("res/inventory/guertel" + itemNr + ".png")); break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void generateItemStats(){
        if(itemNr != 0){
            switch(this.typ){
                case 0:
                    if(Math.random() < 0.2 + this.itemNr * 0.13){
                        this.health = 10+ (int) (Math.random()* (itemNr+2))*7;
                    }
                    if(Math.random() < 0.2 + this.itemNr * 0.13){
                        this.armor = 3+ (int) (Math.random()* (itemNr+2))*2;
                    }
                    if(Math.random() < 0.2 + this.itemNr * 0.13){
                        this.movementspeed = 0.01 + (int) (Math.random()* (itemNr+2))*0.02;
                    }
                    break;
                case 1:
                    if(Math.random() < 0.2 + this.itemNr * 0.13){
                        this.health = 10+ (int) (Math.random()* (itemNr+3))*7;
                    }
                    this.armor = 5+ (int) (Math.random()* (itemNr+3))*4;
                    if(Math.random() < 0.2 + this.itemNr * 0.13){
                        this.movementspeed = 0.01 + (int) (Math.random()* (itemNr+3))*0.02;
                    }
                    break;
                case 2:
                    if(Math.random() < 0.83 + this.itemNr * 0.13){
                        this.damage = 2 + (int) ((Math.random()+2)* (itemNr))*3;
                    }
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.health = 5 + (int) (Math.random()* (itemNr+3))*4;
                    }
                    break;
                case 3:
                    this.movementspeed = 0.05 + (int) ((Math.random()+1)*(itemNr))*0.04;
                    break;
                case 4:
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.health = 5+ (int) (Math.random()* (itemNr+5))*7;
                    }
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.armor = 3+ (int) (Math.random()* (itemNr+3))*2;
                    }
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.movementspeed = 0.01 + (int) (Math.random()* (itemNr+3))*0.01;
                    }
                    break;
                case 5:
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.health = 15+ (int) (Math.random()* (itemNr+5))*7;
                    }
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.armor = 1+ (int) (Math.random()* (itemNr+3))*2;
                    }
                    if(Math.random() < 0.3 + this.itemNr * 0.13){
                        this.movementspeed = 0.01 + (int) (Math.random()* (itemNr+3))*0.01;
                    }
                    break;
                case 6:
                    this.health = 20+ (int) (Math.random()* (itemNr+4))*10;
                    break;
            }
        }

    }
}

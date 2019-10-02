package lolz.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Item {
    int health, damage, abilitypower, armor;
    int typ, itemNr; //schwert, hut etc. (0-6)  stufe(0-4)
    double movementspeed;
    Image image;

    public Item(int health, int damage, int abilitypower, int armor, double movementspeed,int typ, int itemNr) {
        this.health = health;
        this.damage = damage;
        this.abilitypower = abilitypower;
        this.armor = armor;
        this.movementspeed = movementspeed;
        this.typ = typ;
        this.itemNr = itemNr;
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
}

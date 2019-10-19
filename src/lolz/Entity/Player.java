package lolz.Entity;

import lolz.GUI.Inventory;
import lolz.GUI.Item;
import lolz.Main;
import lolz.Maps.Map;
import lolz.Maps.RandomMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public abstract class Player extends Entity {
    public boolean hasDamaged, holdAttack; // variable true if user makes character hit
    // Ingame stats
    public int level, exp, gold;
    private double baseSpeed;
    public Inventory inventory;

    public Player(Map map, int x, int y) {
        // setup player stats
        super(map, x, y, 100, 10, 30, 0.15);
        this.health = 50; // test
        this.level = 1;

        this.width = 45;
        holdAttack = false;
        inventory = new Inventory(this);
    }

    // set Getters and Setters for attribute hit
    public boolean getHitting() {
        return isHitting;
    }

    public void setHitting(boolean isHitting) {
        if (this.isHitting != isHitting) {
            if (isHitting) {
                // slow player down while attacking
            } else {
                // restore speed and attacking status
                this.hasDamaged = false;
            }
            this.animation_state = 0;
        }
        this.isHitting = isHitting;
    }


    public void update(int time) {
        // update player graphic stats
        if (isHitting) {
            this.animation_state += (float) time / 100;
            if(this instanceof Mage) {
                this.allowedToMove = false;
                if (this.animation_state >= 5 && !this.holdAttack) {
                    this.setHitting(false);
                    this.allowedToMove = true;
                }
                if (this.animation_state > 5.5) {
                    animation_state -= 5;
                }
            } else{
                if(this.animation_state >= 5){
                    this.setHitting(false);
                }
            }
        } else if (isMoving) {
            this.animation_state += (float) time / 100;
            this.animation_state %= 6;
        } else {
            this.animation_state += (float) time / 150;
            this.animation_state %= 4;
        }

        if (this.allowedToMove) {
            int oldX = this.getVirtualLeftX();
            int oldY = this.getVirtualY();

            this.move(time);

            if (oldX != this.getVirtualLeftX() || oldY != this.getVirtualY()) {
                if (this.map.debugging) {
                    this.map.paintDebug();
                }
                // update paths to player
                this.map.generatePathsToPlayer();
            }
        }

        updatePlayerStats();
    }

    // check if any given entity is "touching" the hero or rather if the hero is touching it
    public boolean overlap(Entity entity) {
        for (int i : new int[]{0, entity.getWidth()}) { //checking for the left and right border of the entity's image
            for (int j : new int[]{0, entity.getHeight()}) { //checking for the top and bottom border of the entity's image
                //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                if ((this.getX() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getWidth()) && (this.getY() - this.getHeight() <= entity.getY() - j) && (entity.getY() - j <= this.getY())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void giveXP(int amount) {
        if (this.exp + amount >= 90 + 10 * this.level * this.level) {
            this.exp = this.exp + amount - (90 + 10 * this.level * this.level);
            this.level++;
        } else {
            this.exp += amount;
        }
    }

    private void updatePlayerStats() {
        this.maxHealth = 90 + 10 * this.level;
        this.damage = 9 + this.level;
        this.armor = 26 + 4 * this.level;
        this.baseSpeed = 0.15;
        for (int i = 0; i < 7; i++) {
            try {
                this.maxHealth += this.inventory.equipment[i].health;
                this.damage += this.inventory.equipment[i].damage;
                this.armor += this.inventory.equipment[i].armor;
                this.baseSpeed += this.inventory.equipment[i].movementspeed;
            } catch(Exception e){

            }
        }
        this.speed = (!(this instanceof Mage))&&this.isHitting ? this.baseSpeed/3 : this.baseSpeed;
        this.health = Math.min(this.health, this.maxHealth);
    }

    public void loadImages(float[] rgba, String idlePath, String runPath, String attackPath) {
        try {
            if (rgba == null) {
                for (int i = 0; i < 4; i++) {
                    this.img[0][i] = ImageIO.read(new File(idlePath + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 6; i++) {
                    this.img[1][i] = ImageIO.read(new File(runPath + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 5; i++) {
                    this.img[2][i] = ImageIO.read(new File(attackPath + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    this.img[0][i] = tint(rgba[0], rgba[1], rgba[2], rgba[3], ImageIO.read(new File(idlePath + i + ".png"))).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 6; i++) {
                    this.img[1][i] = tint(rgba[0], rgba[1], rgba[2], rgba[3], ImageIO.read(new File(runPath + i + ".png"))).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 5; i++) {
                    this.img[2][i] = tint(rgba[0], rgba[1], rgba[2], rgba[3], ImageIO.read(new File(attackPath + i + ".png"))).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

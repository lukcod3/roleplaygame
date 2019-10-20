package lolz.Entity;


import lolz.GUI.Inventory;
import lolz.Main;
import lolz.Maps.Hub;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public abstract class Player extends Entity {
    public boolean holdAttack; // variable true if user makes character hit
    // Ingame stats
    public int level, exp, gold;
    private double baseSpeed;
    public Inventory inventory;
    Image[][] portal;
    public double portalState;
    public boolean goBack, backport;
    public int[] oldCoordinates;
    public boolean readyToPort;

    public Player(Map map, int x, int y) {
        // setup player stats
        super(map, x, y, 100, 10, 30, 0.15);
        this.health = 50; // test
        this.level = 1;

        oldCoordinates = new int[2];
        this.width = 45;
        holdAttack = false;
        inventory = new Inventory(this);
        this.gold = 0;
        portal = new Image[3][8];

        try {
            for (int j = 0; j < 8; j++) {
                portal[0][j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 0, 64, 64).getScaledInstance(120, -1, Image.SCALE_DEFAULT);
            }
            for (int j = 0; j < 8; j++) {
                portal[1][j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 64, 64, 64).getScaledInstance(120, -1, Image.SCALE_DEFAULT);
            }
            for (int j = 0; j < 5; j++) {
                portal[2][j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 64, 64, 64).getScaledInstance(120, -1, Image.SCALE_DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        portalState = 0;
    }

    public String getStats() {
        return (this instanceof Mage) + "\n" + this.level + "\n" + this.exp + "\n" + this.gold + this.inventory.getStats();
    }

    public void paint(Graphics g) {
        if (backport) {
            if (readyToPort) {
                if (portalState < 5) {
                    g.drawImage(portal[2][(int) portalState], oldCoordinates[0] + 50, oldCoordinates[1] + 50, null);
                } else {
                    goBack = true;
                    readyToPort = false;
                    backport = false;
                }

            } else if (portalState < 8) {
                g.drawImage(portal[1][(int) portalState], oldCoordinates[0] + 50, oldCoordinates[1] + 50, null);
            } else {
                g.drawImage(portal[0][(int) portalState % 8], oldCoordinates[0] + 50, oldCoordinates[1] + 50, null);
                if (x < oldCoordinates[0] + 150 && x > oldCoordinates[0] + 70 && y < oldCoordinates[1] + 160 && y > oldCoordinates[1] + 60) {
                    readyToPort = true;
                    allowedToMove = false;
                    portalState = 0;
                }
            }
        }
    }

    public void update(int time) {
        // update player graphic stats
        if (backport) portalState += 0.1;
        if (isHitting) {
            this.animation_state += (float) time / 100;
            if (this instanceof Mage) {
                this.allowedToMove = false;
                if (this.animation_state >= 5 && !this.holdAttack) {
                    this.setHitting(false);
                    this.allowedToMove = true;
                }
                if (this.animation_state > 5.5) {
                    animation_state -= 5;
                }
            } else {
                if (this.animation_state >= 5) {
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
            } catch (Exception ignore) {

            }
        }
        this.speed = (!(this instanceof Mage)) && this.isHitting ? this.baseSpeed / 3 : this.baseSpeed;
        this.health = Math.min(this.health, this.maxHealth);
        if (this.map instanceof Hub) {
            this.health = this.maxHealth;
        }
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

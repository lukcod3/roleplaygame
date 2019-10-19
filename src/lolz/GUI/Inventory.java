package lolz.GUI;

import lolz.Entity.Fighter;
import lolz.Entity.Player;
import lolz.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;

public class Inventory {
    public Item[] equipment; // 1 is hat, 2 is t-shirt, 3 is sword, 4 is shoes, 5 is necklace, 6 is ring, 7 is belt, 8-11 is depot
    public int aktInventar;
    public Image[] inventoryImages;
    public Item[][] item;
    public Image emptyInventory;
    public boolean showButton, readyForSwitch, statsShown;
    public Point frameLocation;
    public int[] oldCoordinates;
    int X_POSITION_IMAGE_INVENTORY = 560;
    int Y_POSITION_IMAGE_INVENTORY = 185;
    public Player player;
    public int hoverInventory;

    public Inventory(Player player) {
        this.oldCoordinates = new int[2];
        this.player = player;
        try {
            inventoryImages = new Image[4];
            inventoryImages[0] = ImageIO.read(new File("res/inventory/gegenstandAblegen_aus.png"));
            inventoryImages[1] = ImageIO.read(new File("res/inventory/gegenstandAblegen_an.png"));
            inventoryImages[2] = ImageIO.read(new File("res/inventory/anlegen_aus.png"));
            inventoryImages[3] = ImageIO.read(new File("res/inventory/anlegen_an.png"));
            emptyInventory = ImageIO.read(new File("res/inventory/freiPlatz.png")).getScaledInstance(60, -1, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // initialize Items
        item = new Item[7][5];
        for (int i = 0; i <= 4; i++) {
            item[0][i] = new Item(0, i);
            item[1][i] = new Item(1, i);
            item[2][i] = new Item(2, i);
            item[3][i] = new Item(3, i);

            item[4][i] = new Item(4, i);
            item[5][i] = new Item(5, i);
            item[6][i] = new Item(6, i);
        }
        this.equipment = new Item[]{item[0][0], item[1][0], item[2][0], item[3][0], item[4][0], item[5][0], item[6][0], null, null, null, null};

        testGeneratedRandomItemSet();
        hoverInventory = 0;
    }

    public void printStats(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 25));
        g.drawString(""+ player.gold, 20, 130);
        g.drawImage(Main.goldImage, 60, 107 ,null);
        // paint player stats
        if (statsShown) {
            // draw stats
            Color myColor = new Color(56, 56, 56, 165);
            Font titleF = new Font("SansSerif", Font.BOLD, 25);
            Font statsF = new Font("SansSerif", Font.PLAIN, 15);
            g.setColor(Color.black);
            g.drawRect(150, 50, 660, 440);
            g.setColor(myColor);
            g.fillRect(150, 50, 660, 440);
            g.setFont(titleF);
            g.setColor(Color.white);
            g.drawString("Profil", 260, 80);
            g.drawString("Inventar", 570, 80);
            g.setFont(statsF);
            g.drawString("Leben............................(" + this.player.health + "/" + this.player.maxHealth + ")", 200, 130);
            g.drawString("Angriffsschaden....................." + this.player.damage, 200, 160);
            g.drawString("Rüstung..................................." + this.player.armor, 200, 190);
            g.drawString("Lauftempo..........................." + new DecimalFormat("#.##").format(this.player.speed), 200, 220);

            // draw player
            if (this.player instanceof Fighter) {
                if (this.player.turnedRight) {
                    if (this.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        g.drawImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_INVENTORY, Y_POSITION_IMAGE_INVENTORY, null); // set player's animation to hit animation
                    } else if (this.player.isMoving) {
                        g.drawImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_INVENTORY, Y_POSITION_IMAGE_INVENTORY, null);
                    } else {
                        g.drawImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_INVENTORY, Y_POSITION_IMAGE_INVENTORY, null);
                    }
                } else {
                    if (this.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        Main.drawReflectImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_INVENTORY, Y_POSITION_IMAGE_INVENTORY);
                    } else if (this.player.isMoving) {
                        Main.drawReflectImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_INVENTORY, Y_POSITION_IMAGE_INVENTORY);
                    } else {
                        Main.drawReflectImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_INVENTORY, Y_POSITION_IMAGE_INVENTORY);
                    }
                }
            } else {
                if (!this.player.turnedRight) {
                    if (this.player.isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        int offset = (int) ((1.6 * this.player.img[2][(int) this.player.animation_state % 5].getWidth(null) - this.player.width) / 2);
                        g.drawImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_INVENTORY - offset, Y_POSITION_IMAGE_INVENTORY, null); // set player's animation to hit animation
                    } else if (this.player.isMoving) {
                        int offset = (int) ((1.6 * this.player.img[1][(int) this.player.animation_state % 6].getWidth(null) - this.player.width) / 2);
                        g.drawImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_INVENTORY - offset, Y_POSITION_IMAGE_INVENTORY, null);
                    } else {
                        int offset = (int) ((1.6 * this.player.img[0][(int) this.player.animation_state % 4].getWidth(null) - this.player.width) / 2);
                        g.drawImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), X_POSITION_IMAGE_INVENTORY - offset, Y_POSITION_IMAGE_INVENTORY, null);
                    }
                } else {
                    if (this.player.isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                        int offset = (int) ((1.6 * this.player.img[2][(int) this.player.animation_state % 5].getWidth(null) - this.player.width) / 2);
                        Main.drawReflectImage(this.player.img[2][(int) this.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_INVENTORY + offset, Y_POSITION_IMAGE_INVENTORY);
                    } else if (this.player.isMoving) {
                        int offset = (int) ((1.6 * this.player.img[1][(int) this.player.animation_state % 6].getWidth(null) - this.player.width) / 2);
                        Main.drawReflectImage(this.player.img[1][(int) this.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_INVENTORY + offset, Y_POSITION_IMAGE_INVENTORY);
                    } else {
                        int offset = (int) ((1.6 * this.player.img[0][(int) this.player.animation_state % 4].getWidth(null) - this.player.width) / 2);
                        Main.drawReflectImage(this.player.img[0][(int) this.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, X_POSITION_IMAGE_INVENTORY + offset, Y_POSITION_IMAGE_INVENTORY);
                    }
                }
            }
            // draw inventory images
            g.drawImage(this.equipment[0].image, 590, 100, null);
            g.drawImage(this.equipment[1].image, 500, 180, null);
            g.drawImage(this.equipment[2].image, 500, 255, null);
            g.drawImage(this.equipment[3].image, 590, 330, null);
            g.drawImage(this.equipment[4].image, 680, 155, null);
            g.drawImage(this.equipment[5].image, 680, 230, null);
            g.drawImage(this.equipment[6].image, 680, 305, null);
            for (int i = 0; i <= 3; i++) {
                Image a;
                if (this.equipment[i + 7] == null) {
                    a = emptyInventory;
                } else {
                    a = this.equipment[i + 7].image;
                }
                g.drawImage(a, 500 + (i * 61), 420, null);
            }
            if (showButton && aktInventar > 0 && aktInventar < 8) {
                if (isCourserInRectangle(oldCoordinates[0] + 10, oldCoordinates[0] + 255, oldCoordinates[1] - 30, oldCoordinates[1] + 3)) {
                    g.drawImage(inventoryImages[1].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForSwitch = true;
                } else {
                    g.drawImage(inventoryImages[0].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForSwitch = false;
                }
            } else if (showButton && aktInventar > 7 && aktInventar < 12) {
                if (isCourserInRectangle(oldCoordinates[0] + 10, oldCoordinates[0] + 255, oldCoordinates[1] - 30, oldCoordinates[1] + 3)) {
                    g.drawImage(inventoryImages[3].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForSwitch = true;
                } else {
                    g.drawImage(inventoryImages[2].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForSwitch = false;
                }
            } else {
                readyForSwitch = false;
                if (hoverInventory != 0) {
                    equipment[hoverInventory - 1].drawItemStats(g);
                }
            }
        }
    }

    private boolean isCourserInRectangle(double x1, double x2, double y1, double y2) {
        return ((Main.mouseCoordinates[0] <= x2) && (Main.mouseCoordinates[0] >= x1) && (Main.mouseCoordinates[1] <= y2) && (Main.mouseCoordinates[1] >= y1));
    }

    private void testGeneratedRandomItemSet() {
        for (int i = 0; i <= 6; i++) {
            this.equipment[i] = item[i][(int) (Math.random() * 5)];
        }
        this.equipment[7] = item[(int) (Math.random() * 7)][(int) (Math.random() * 4) + 1];
        this.equipment[8] = item[(int) (Math.random() * 7)][(int) (Math.random() * 4) + 1];
    }

    public void updateInventory(MouseEvent e) {
        // check if courser is on inventory field
        hoverInventory = 0;
        if (e.getButton() == 3 && statsShown) {
            if (isCourserInRectangle(590, 650, 100, 160) && equipment[0].itemNr != 0) {
                aktInventar = 1;
            } else if (isCourserInRectangle(500, 560, 180, 240) && equipment[1].itemNr != 0) {
                aktInventar = 2;
            } else if (isCourserInRectangle(500, 560, 255, 315) && equipment[2].itemNr != 0) {
                aktInventar = 3;
            } else if (isCourserInRectangle(590, 640, 330, 390) && equipment[3].itemNr != 0) {
                aktInventar = 4;
            } else if (isCourserInRectangle(680, 730, 155, 215) && equipment[4].itemNr != 0) {
                aktInventar = 5;
            } else if (isCourserInRectangle(680, 730, 230, 290) && equipment[5].itemNr != 0) {
                aktInventar = 6;
            } else if (isCourserInRectangle(680, 730, 305, 365) && equipment[6].itemNr != 0) {
                aktInventar = 7;
            } else if (isCourserInRectangle(500, 560, 420, 480) && equipment[7] != null) {
                aktInventar = 8;
            } else if (isCourserInRectangle(561, 621, 420, 480) && equipment[8] != null) {
                aktInventar = 9;
            } else if (isCourserInRectangle(622, 682, 420, 480) && equipment[9] != null) {
                aktInventar = 10;
            } else if (isCourserInRectangle(683, 743, 420, 480) && equipment[10] != null) {
                aktInventar = 11;

            } else {
                aktInventar = 0;
            }
            if (aktInventar != 0) {
                showButton = true;
                oldCoordinates[0] = Main.mouseCoordinates[0];
                oldCoordinates[1] = Main.mouseCoordinates[1];
            }
            // switches Item places
        } else if (e.getButton() == 1) {
            if (readyForSwitch) {
                readyForSwitch = false;
                showButton = false;
                if (aktInventar > 0 && aktInventar < 8) {
                    for (int i = 0; i < 4; i++) {
                        if (equipment[7 + i] == null) {
                            equipment[7 + i] = equipment[aktInventar - 1];
                            equipment[aktInventar - 1] = item[aktInventar - 1][0];
                            break;
                        }
                    }

                } else {
                    Item hilf = equipment[aktInventar - 1];
                    if (equipment[hilf.typ] == item[hilf.typ][0]) {
                        equipment[aktInventar - 1] = null;
                    } else {
                        equipment[aktInventar - 1] = equipment[hilf.typ];
                    }
                    equipment[hilf.typ] = hilf;
                }
            } else {
                showButton = false;
            }
        } else {
            if (isCourserInRectangle(590, 650, 100, 160) && equipment[0].itemNr != 0) {
                hoverInventory = 1;
            } else if (isCourserInRectangle(500, 560, 180, 240) && equipment[1].itemNr != 0) {
                hoverInventory = 2;
            } else if (isCourserInRectangle(500, 560, 255, 315) && equipment[2].itemNr != 0) {
                hoverInventory = 3;
            } else if (isCourserInRectangle(590, 640, 330, 390) && equipment[3].itemNr != 0) {
                hoverInventory = 4;
            } else if (isCourserInRectangle(680, 730, 155, 215) && equipment[4].itemNr != 0) {
                hoverInventory = 5;
            } else if (isCourserInRectangle(680, 730, 230, 290) && equipment[5].itemNr != 0) {
                hoverInventory = 6;
            } else if (isCourserInRectangle(680, 730, 305, 365) && equipment[6].itemNr != 0) {
                hoverInventory = 7;
            } else if (isCourserInRectangle(500, 560, 420, 480) && equipment[7] != null) {
                hoverInventory = 8;
            } else if (isCourserInRectangle(561, 621, 420, 480) && equipment[8] != null) {
                hoverInventory = 9;
            } else if (isCourserInRectangle(622, 682, 420, 480) && equipment[9] != null) {
                hoverInventory = 10;
            } else if (isCourserInRectangle(683, 743, 420, 480) && equipment[10] != null) {
                hoverInventory = 11;
            }
        }
    }
}

package lolz.Entity;

import lolz.GUI.Item;
import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class Shopkeeper {
    private Image[] images;
    public double animation_state;
    public boolean showShop;
    public Player player;
    private int X_POSITION_IMAGE_SHOP = 560;
    private int Y_POSITION_IMAGE_SHOP = 185;
    private Item[] shop;
    private boolean showButton, readyForBuy;
    private int[] oldCoordinates;
    private int aktInventar, hoverInventory;
    private Image[] inventoryImages;
    private int aktShopInventar, hoverShop;

    public Shopkeeper(Player player) {
        this.oldCoordinates = new int[2];
        images = new Image[4];
        inventoryImages = new Image[4];
        aktShopInventar = 0;
        hoverShop = 0;
        try {
            for (int i = 0; i <= 3; i++) {
                images[i] = ImageIO.read(new File("res/monster/Phantom Knight/Individual Sprites/phantom knight-idle-0" + i + ".png")).getScaledInstance(100, -1, Image.SCALE_DEFAULT);
            }
            inventoryImages[0] = ImageIO.read(new File("res/inventory/kaufen_aus.png"));
            inventoryImages[1] = ImageIO.read(new File("res/inventory/kaufen_an.png"));
            inventoryImages[2] = ImageIO.read(new File("res/inventory/verkaufen_aus.png"));
            inventoryImages[3] = ImageIO.read(new File("res/inventory/verkaufen_an.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation_state = 0;
        showShop = false;
        this.player = player;
        getShopItems();
    }

    public void update() {
        animation_state += 0.1;
        animation_state %= 4;
    }

    public void paint(Graphics g) {
        Main.drawReflectImage(images[(int) (animation_state)], g, 60, 330);
    }

    // called in hub
    public void printShop(Graphics g) {
        if (showShop) {
            Color myColor = new Color(56, 56, 56, 230);
            Font titleF = new Font("SansSerif", Font.BOLD, 25);
            g.drawRect(150, 50, 660, 440);
            g.setColor(myColor);
            g.fillRect(150, 50, 660, 440);
            g.setFont(titleF);
            g.setColor(Color.white);
            g.drawString("Shop", 260, 80);
            g.drawString("Inventar", 570, 80);
            g.setFont(new Font("SansSerif", Font.PLAIN, 15));

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
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (shop[3 * i + j] != null) {
                        g.drawImage(shop[3 * i + j].image, 200 + 61 * j, 300 + 61 * i, null);
                    } else {
                        g.drawImage(this.player.inventory.emptyInventory, 200 + 61 * j, 300 + 61 * i, null);
                    }
                }
            }
            if (showButton && aktInventar > 0 && aktInventar < 12) {
                if (isCourserInRectangle(oldCoordinates[0] + 10, oldCoordinates[0] + 255, oldCoordinates[1] - 30, oldCoordinates[1] + 3)) {
                    g.drawImage(inventoryImages[3].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForBuy = true;
                } else {
                    g.drawImage(inventoryImages[2].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForBuy = false;
                }
            } else if (showButton && aktShopInventar > 0 && aktShopInventar < 7) {
                if (isCourserInRectangle(oldCoordinates[0] + 10, oldCoordinates[0] + 255, oldCoordinates[1] - 30, oldCoordinates[1] + 3)) {
                    g.drawImage(inventoryImages[1].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForBuy = true;
                } else {
                    g.drawImage(inventoryImages[0].getScaledInstance(235, -1, Image.SCALE_DEFAULT), oldCoordinates[0] + 10, oldCoordinates[1] - 30, null);
                    readyForBuy = false;
                }
            } else {
                readyForBuy = false;
                if (hoverInventory != 0) {
                    this.player.inventory.equipment[hoverInventory - 1].drawItemStats(g, false);
                }
                if (hoverShop != 0) {
                    this.shop[hoverShop - 1].drawItemStats(g, true);
                }
            }
        }
    }

    private void getShopItems() {
        shop = new Item[6];
        for (int i = 0; i <= 5; i++) {
            int stufe;

            if ((int) (1 + Math.random() * 10) * this.player.level > 60) {
                stufe = 4;
            } else if ((int) (1 + Math.random() * 10) * this.player.level > 40) {
                stufe = 3;
            } else if ((int) (1 + Math.random() * 10) * this.player.level > 15) {
                stufe = 2;
            } else {
                stufe = 1;
            }
            shop[i] = this.player.inventory.item[(int) (Math.random() * 7)][stufe];
        }
    }

    public void updateShop(MouseEvent e) {
        hoverInventory = 0;
        hoverShop = 0;
        if (e.getButton() == 3 && showShop) {
            aktInventar = 0;
            aktShopInventar = 0;
            if (isCourserInRectangle(590, 650, 100, 160) && this.player.inventory.equipment[0].itemNr != 0) {
                aktInventar = 1;
            } else if (isCourserInRectangle(500, 560, 180, 240) && this.player.inventory.equipment[1].itemNr != 0) {
                aktInventar = 2;
            } else if (isCourserInRectangle(500, 560, 255, 315) && this.player.inventory.equipment[2].itemNr != 0) {
                aktInventar = 3;
            } else if (isCourserInRectangle(590, 650, 330, 390) && this.player.inventory.equipment[3].itemNr != 0) {
                aktInventar = 4;
            } else if (isCourserInRectangle(680, 740, 155, 215) && this.player.inventory.equipment[4].itemNr != 0) {
                aktInventar = 5;
            } else if (isCourserInRectangle(680, 740, 230, 290) && this.player.inventory.equipment[5].itemNr != 0) {
                aktInventar = 6;
            } else if (isCourserInRectangle(680, 740, 305, 365) && this.player.inventory.equipment[6].itemNr != 0) {
                aktInventar = 7;
            } else if (isCourserInRectangle(500, 560, 420, 480) && this.player.inventory.equipment[7] != null) {
                aktInventar = 8;
            } else if (isCourserInRectangle(561, 621, 420, 480) && this.player.inventory.equipment[8] != null) {
                aktInventar = 9;
            } else if (isCourserInRectangle(622, 682, 420, 480) && this.player.inventory.equipment[9] != null) {
                aktInventar = 10;
            } else if (isCourserInRectangle(683, 743, 420, 480) && this.player.inventory.equipment[10] != null) {
                aktInventar = 11;
            } else if (isCourserInRectangle(200, 260, 300, 360) && this.shop[0] != null) {
                aktShopInventar = 1;
            } else if (isCourserInRectangle(261, 321, 300, 360) && this.shop[1] != null) {
                aktShopInventar = 2;
            } else if (isCourserInRectangle(322, 382, 300, 360) && this.shop[2] != null) {
                aktShopInventar = 3;
            } else if (isCourserInRectangle(200, 260, 361, 421) && this.shop[3] != null) {
                aktShopInventar = 4;
            } else if (isCourserInRectangle(261, 321, 361, 421) && this.shop[4] != null) {
                aktShopInventar = 5;
            } else if (isCourserInRectangle(322, 382, 361, 421) && this.shop[5] != null) {
                aktShopInventar = 6;
            }
            if (aktInventar != 0) {
                showButton = true;
                oldCoordinates[0] = Main.mouseCoordinates[0];
                oldCoordinates[1] = Main.mouseCoordinates[1];
            }
            if (aktShopInventar != 0) {
                showButton = true;
                oldCoordinates[0] = Main.mouseCoordinates[0];
                oldCoordinates[1] = Main.mouseCoordinates[1];
            }
            // switches Item places
        } else if (e.getButton() == 1) {
            if (readyForBuy) {
                readyForBuy = false;
                showButton = false;
                if (aktInventar > 0 && aktInventar < 8) {
                    this.player.gold += this.player.inventory.equipment[aktInventar - 1].verkaufen;
                    this.player.inventory.equipment[aktInventar - 1] = this.player.inventory.item[this.player.inventory.equipment[aktInventar - 1].typ][0];
                } else if (aktInventar > 7 && aktInventar < 12) {
                    this.player.gold += this.player.inventory.equipment[aktInventar - 1].verkaufen;
                    this.player.inventory.equipment[aktInventar - 1] = null;
                } else if (aktShopInventar > 0 && aktShopInventar < 7 && this.shop[aktShopInventar - 1].kaufen <= this.player.gold) {
                    if (this.player.inventory.equipment[shop[aktShopInventar - 1].typ].itemNr == 0) {
                        this.player.inventory.equipment[shop[aktShopInventar - 1].typ] = shop[aktShopInventar - 1];
                        this.player.gold -= this.shop[aktShopInventar-1].kaufen;
                        this.shop[aktShopInventar-1] = null;
                    } else {
                        for(int i = 0; i<=3; i++){
                            if(this.player.inventory.equipment[7+i]==null){
                                this.player.inventory.equipment[7+i] = shop[aktShopInventar - 1];
                                this.player.gold -= this.shop[aktShopInventar-1].kaufen;
                                this.shop[aktShopInventar-1] = null;
                            }
                        }
                    }
                }
            } else {
                showButton = false;
            }
        } else {
            if (isCourserInRectangle(590, 650, 100, 160) && this.player.inventory.equipment[0].itemNr != 0) {
                hoverInventory = 1;
            } else if (isCourserInRectangle(500, 560, 180, 240) && this.player.inventory.equipment[1].itemNr != 0) {
                hoverInventory = 2;
            } else if (isCourserInRectangle(500, 560, 255, 315) && this.player.inventory.equipment[2].itemNr != 0) {
                hoverInventory = 3;
            } else if (isCourserInRectangle(590, 650, 330, 390) && this.player.inventory.equipment[3].itemNr != 0) {
                hoverInventory = 4;
            } else if (isCourserInRectangle(680, 740, 155, 215) && this.player.inventory.equipment[4].itemNr != 0) {
                hoverInventory = 5;
            } else if (isCourserInRectangle(680, 740, 230, 290) && this.player.inventory.equipment[5].itemNr != 0) {
                hoverInventory = 6;
            } else if (isCourserInRectangle(680, 740, 305, 365) && this.player.inventory.equipment[6].itemNr != 0) {
                hoverInventory = 7;
            } else if (isCourserInRectangle(500, 560, 420, 480) && this.player.inventory.equipment[7] != null) {
                hoverInventory = 8;
            } else if (isCourserInRectangle(561, 621, 420, 480) && this.player.inventory.equipment[8] != null) {
                hoverInventory = 9;
            } else if (isCourserInRectangle(622, 682, 420, 480) && this.player.inventory.equipment[9] != null) {
                hoverInventory = 10;
            } else if (isCourserInRectangle(683, 743, 420, 480) && this.player.inventory.equipment[10] != null) {
                hoverInventory = 11;
            } else if (isCourserInRectangle(200, 260, 300, 360)&& this.shop[0] != null) {
                hoverShop = 1;
            } else if (isCourserInRectangle(261, 321, 300, 360)&& this.shop[1] != null) {
                hoverShop = 2;
            } else if (isCourserInRectangle(322, 382, 300, 360)&& this.shop[2] != null) {
                hoverShop = 3;
            } else if (isCourserInRectangle(200, 260, 361, 421)&& this.shop[3] != null) {
                hoverShop = 4;
            } else if (isCourserInRectangle(261, 321, 361, 421)&& this.shop[4] != null) {
                hoverShop = 5;
            } else if (isCourserInRectangle(322, 382, 361, 421)&& this.shop[5] != null) {
                hoverShop = 6;
            }
        }

    }

    private boolean isCourserInRectangle(double x1, double x2, double y1, double y2) {
        return ((Main.mouseCoordinates[0] <= x2) && (Main.mouseCoordinates[0] >= x1) && (Main.mouseCoordinates[1] <= y2) && (Main.mouseCoordinates[1] >= y1));
    }
}

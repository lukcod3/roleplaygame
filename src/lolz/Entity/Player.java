package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Player extends Entity {
    private Map map;
    public volatile boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right
    public Image[][] img;
    public Image hitImage; // Image for hit animation didn't belong to the Image array, so it's assigned to a new attribute
    public boolean isHitting, hasDamaged; // variable true if user makes character hit
    public boolean isMoving;
    public double animation_state;
    public final String base_char = "elf_m";
    public int[] equipment; // 1 is hat, 2 is t-shirt, 3 is sword, 4 is shoes, 5 is necklace, 6 is ring, 7 is belt, 8-11 is depot
    public Image[][] inventoryImages;
    public Image empty;
    public boolean turnedRight;

    // Ingame stats
    public int abilitypower, level, exp, gold;

    public Player(Map map, int x, int y) {
        // setup player stats
        super(x, y, 100, 10, 30, 0.15);
        this.level = 1;
        this.turnedRight = true;
        this.map = map;
        this.x = x;
        this.y = y;
        this.directions = new boolean[4];
        this.img = new Image[3][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];

        this.width = 90;
        // 1-7 weared inventory int is level of equipment, 8-11 free inventory space contains level and type of equipment(includes int from 0-28)
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/Individual Sprites/adventurer-idle-0" + i + ".png")).getScaledInstance(this.width, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 6; i++) {
                img[1][i] = ImageIO.read(new File("res/Individual Sprites/adventurer-run-0" + i + ".png")).getScaledInstance(this.width, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 5; i++) {
                img[2][i] = ImageIO.read(new File("res/Individual Sprites/adventurer-attack1-0" + i + ".png")).getScaledInstance(this.width, -1, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.width = 45;
        this.height = img[0][0].getHeight(null);

        // rearrange y (given x and y values are for the bottom left corner)
        // this.x -= this.width;
        this.y -= this.height;

        // setup player inventory
        equipment = new int[] {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        inventoryImages = new Image[7][5];
        try {
            inventoryImages[0][0] = ImageIO.read(new File("res/inventory/hutPlatz.png"));
            inventoryImages[1][0] = ImageIO.read(new File("res/inventory/ruestungPlatz.png"));
            inventoryImages[2][0] = ImageIO.read(new File("res/inventory/schwertPlatz.png"));
            inventoryImages[3][0] = ImageIO.read(new File("res/inventory/schuhePlatz.png"));
            inventoryImages[4][0] = ImageIO.read(new File("res/inventory/kettePlatz.png"));
            inventoryImages[5][0] = ImageIO.read(new File("res/inventory/ringPlatz.png"));
            inventoryImages[6][0] = ImageIO.read(new File("res/inventory/guertelPlatz.png"));
            empty = ImageIO.read(new File("res/inventory/freiPlatz.png"));

            for (int i = 1; i <= 4; i++) {
                //inventoryImages[0][i] = ImageIO.read(new File("res/inventory/hut"+i+".png"));
                //inventoryImages[1][i] = ImageIO.read(new File("res/inventory/ruestung"+i+".png"));
                inventoryImages[2][i] = ImageIO.read(new File("res/inventory/schwert" + i + ".png"));
                //inventoryImages[3][i] = ImageIO.read(new File("res/inventory/schuhe"+i+".png"));
                //inventoryImages[4][i] = ImageIO.read(new File("res/inventory/kette"+i+".png"));
                //inventoryImages[5][i] = ImageIO.read(new File("res/inventory/ring"+i+".png"));
                //inventoryImages[6][i] = ImageIO.read(new File("res/inventory/guertel"+i+".png"));
            }

        } catch (Exception e) {
            System.out.println("Fehler");
        }
        if(inventoryImages[2][1]==null){
            System.out.println(1);
        }
    }

    // set Getters and Setters for attribute hit
    public boolean getHitting() {
        return isHitting;
    }

    public void setHitting(boolean isHitting) {
        if (this.isHitting != isHitting) {
            if (isHitting) {
                // slow player down while attacking
                this.speed = 0.05;
            } else {
                // restore speed and attacking status
                this.speed = 0.15;
                this.hasDamaged = false;
            }
            this.animation_state = 0;
        }
        this.isHitting = isHitting;
    }

    public void paint(Graphics g) {
        // System.out.println(map.get_tile_at((int) (this.x), (int) (this.y + this.height)).toString());
        // paint player
        g.setColor(Color.BLACK);
        if (turnedRight) {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (img[2][(int) this.animation_state].getWidth(null) - this.width) / 2;
                g.drawImage(img[2][(int) this.animation_state], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (img[1][(int) this.animation_state].getWidth(null) - this.width) / 2;
                g.drawImage(img[1][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (img[0][(int) this.animation_state].getWidth(null) - this.width) / 2;
                g.drawImage(img[0][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            }
        } else {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (img[2][(int) this.animation_state].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[2][(int) this.animation_state], g, (int) this.x - offset, (int) this.y);
            } else if (isMoving) {
                int offset = (img[1][(int) this.animation_state].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[1][(int) this.animation_state], g, (int) this.x - offset, (int) this.y);
            } else {
                int offset = (img[0][(int) this.animation_state].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[0][(int) this.animation_state], g, (int) this.x - offset, (int) this.y);
            }
        }
    }

    public void update(int time) {
        // update player graphic stats
        if (isHitting) {
            this.animation_state += (float) time / 100;
            if (this.animation_state >= 5) {
                this.setHitting(false);
            }
        } else if (isMoving) {
            this.animation_state += (float) time / 100;
            this.animation_state %= 6;
        } else {
            this.animation_state += (float) time / 150;
            this.animation_state %= 4;
        }

        overlap(map.monster);

        // old
        // this.animation_state %= 4;

        // count how many directions are active
        int dir_count = 0;
        for (boolean b : this.directions) {
            if (b) {
                dir_count++;
            }
        }

        // if no or opposite keys are pressed the player doesnt move
        if (dir_count == 0 || dir_count > 2 || this.directions[0] && this.directions[2] || this.directions[1] && this.directions[3]) {
            // opposite keys are pressed => player doesnt move
            if (isMoving) {
                isMoving = false;
                animation_state = 0;
            }
            return;
        }

        if (!isMoving) {
            isMoving = true;
            animation_state = 0;
        }
        if (this.directions[3]) {
            turnedRight = true;
        } else if (this.directions[1]) {
            turnedRight = false;
        }

        // calculate how much the player moves
        double movement = this.speed * time;

        // if the player moves in 2 directions he doesnt run faster
        if (dir_count == 2) {
            movement *= 1.0 / Math.pow(2, 0.5);
        }

        // safe previous position for restoring
        double old_x = this.x;
        double old_y = this.y;

        // move player
        if (this.directions[0]) {
            this.y -= movement;
        }
        if (this.directions[1]) {
            this.x -= movement;
        }
        if (this.directions[2]) {
            this.y += movement;
        }
        if (this.directions[3]) {
            this.x += movement;
        }

        // check if player in wall => reset movement
        for (int d_x : new int[]{0, this.width}) {
            if (!map.get_tile_at((int) (this.x + d_x), (int) (this.y + this.height)).isGround()) {
                // player doesnt movedd
                // test if fix is possible
                if (map.get_tile_at((int) (old_x + d_x), (int) (this.y + this.height)).isGround()) {
                    this.x = old_x;
                } else if (map.get_tile_at((int) (this.x + d_x), (int) (old_y + this.height)).isGround()) {
                    this.y = old_y;
                } else {
                    this.x = old_x;
                    this.y = old_y;
                }
            }
        }
    }

    // check if any given monster is "touching" the hero or rather if the hero is touching it
    public boolean overlap(Entity monster) {
        for (int i : new int[]{0, monster.getWidth()}) { //checking for the left and right border of the monster's image
            for (int j : new int[]{0, monster.getHeight()}) { //checking for the top and bottom border of the monster's image
                if ((this.getX() <= monster.getX() + i) && (monster.getX() + i <= this.getX() + this.getWidth()) && (this.getY() <= monster.getY() + j) && (monster.getY() + j <= this.getY() + this.getHeight())) { //if any of the monster's boundaries can be found between any of the hero's boundaries, they touch
                    //System.out.println("Player X: " + this.getX() + " | Y: " + this.getY() + " || Monster X: " + monster.getX() + " | Y: " + monster.getY());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean attack(Entity entity) {
        if (getHitting() && (int) animation_state % 5 == 2 && !hasDamaged) {
            this.hasDamaged = true;
            entity.setHealth(entity.getHealth() - this.damage);
            System.out.println("entity health: " + entity.getHealth());
            if (entity.getHealth() == 0) {
                return true;
            }
        }
        return false;
    }

}

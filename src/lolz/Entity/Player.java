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
    public boolean hit; // variable true if user makes character hit
    public boolean moving;
    public double animation_state;
    public final String base_char = "elf_m";
    public int[] equipment; // 1 is hat, 2 is t-shirt, 3 is sword, 4 is shoes, 5 is ring, 6 is necklace, 7 is belt, 8-11 is depot
    public Image[][] inventoryImages;
    public Image empty;
    public boolean turnedRight;
    public boolean lock;

    // Ingame stats
    public int maxHealth, health, attackdamage, abilitypower, armor, level, exp, gold;

    public Player(Map map, int x, int y) {
        // setup player stats
        super(x, y, 100, 10, 30, 0.15);
        turnedRight = true;
        this.map = map;
        this.x = x;
        this.y = y;
        this.directions = new boolean[4];
        this.img = new Image[3][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];

        this.width = 45;
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

        this.height = img[0][0].getHeight(null);

        // rearrange y (given x and y values are for the bottom left corner)
        // this.x -= this.width;
        this.y -= this.height;

        // setup player inventory
        equipment = new int[]{0, 0, 0, 0, 0, 0, 0};
        inventoryImages = new Image[7][1];
        try {
            inventoryImages[0][0] = ImageIO.read(new File("res/inventory/hutPlatz.png"));
            inventoryImages[1][0] = ImageIO.read(new File("res/inventory/ruestungPlatz.png"));
            inventoryImages[2][0] = ImageIO.read(new File("res/inventory/schwertPlatz.png"));
            inventoryImages[3][0] = ImageIO.read(new File("res/inventory/schuhePlatz.png"));
            inventoryImages[4][0] = ImageIO.read(new File("res/inventory/kettePlatz.png"));
            inventoryImages[5][0] = ImageIO.read(new File("res/inventory/ringPlatz.png"));
            inventoryImages[6][0] = ImageIO.read(new File("res/inventory/guertelPlatz.png"));
            empty = ImageIO.read(new File("res/inventory/freiPlatz.png"));
        } catch (Exception e) {
            System.out.println("Fehler");
        }
    }

    // set Getters and Setters for attribute hit
    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void paint(Graphics g) {
        // System.out.println(map.get_tile_at((int) (this.x), (int) (this.y + this.height)).toString());
        // paint player
        g.setColor(Color.BLACK);
        if(turnedRight){
            if (getHit()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                g.drawImage(img[2][(int) this.animation_state%5], (int) this.x, (int) this.y, null); // set player's animation to hit animation
            } else if (moving) {
                g.drawImage(img[1][(int) this.animation_state%6], (int) this.x, (int) this.y, null);
            } else {
                g.drawImage(img[0][(int) this.animation_state%4], (int) this.x, (int) this.y, null);
            }
        }else{
            if (getHit()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                Main.drawReflectImage(img[2][(int) this.animation_state%5], g, (int) this.x, (int) this.y);
            } else if (moving) {
                Main.drawReflectImage(img[1][(int) this.animation_state%6], g, (int) this.x, (int) this.y);
            } else {
                Main.drawReflectImage(img[0][(int) this.animation_state%4], g, (int) this.x, (int) this.y);
            }
        }
    }

    public void update(int time) {
        // update player graphic stats
        int old_state = (int) this.animation_state;
        if(hit){
            this.animation_state += (float) time / 100;
        }else if (moving) {
            this.animation_state += (float) time / 100;
        } else {
            this.animation_state += (float) time / 150;
        }

        // update player graphic width and height
        if (old_state < (int) this.animation_state) {
            this.animation_state %= 60;
            if (getHit()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                this.height = img[2][(int) this.animation_state%5].getHeight(null);
                this.width = img[2][(int) this.animation_state%5].getWidth(null);
            } else if (moving) {
                this.height = img[1][(int) this.animation_state%6].getHeight(null);
                this.width = img[1][(int) this.animation_state%6].getWidth(null);
            }
        }

        if ((int) this.animation_state%5 != 2 && this.lock) {
            this.lock = false;
            System.out.println(this.lock);
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
            if (moving) {
                moving = false;
                animation_state = 0;
            }
            return;
        }

        if (!moving) {
            moving = true;
            animation_state = 0;
        }
        if(this.directions[3]){
            turnedRight = true;
        }else if(this.directions[1]){
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
                // player doesnt move
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
            for (int j : new int[]{0, monster.getWidth()}) { //checking for the top and bottom border of the monster's image
                if (this.x <= monster.getX() + i && monster.getX() + i <= this.x + this.width && this.y <= monster.getY() + j && monster.getY() + j <= this.y + this.height) { //if any of the monster's boundaries can be found between any of the hero's boundaries, they touch
                    System.out.println("Overlap");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean attack(Entity monster) {
        if (getHit() && (int) animation_state%5 == 2 && !lock) {
            monster.setHealth(monster.getHealth() - this.attackdamage);
            System.out.println("monster health: " + monster.getHealth());
            return true;
        }
        return false;
    }

}

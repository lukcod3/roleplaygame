package lolz.Player;

import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player {
    private Map map;
    public double x, y;
    private int width, height;
    private float speed = 0.15f;
    public volatile boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right
    private Image[][] img;
    private Image hitImage; // Image for hit animation didn't belong to the Image array, so it's assigned to a new attribute
    private boolean hit; // variable true if user makes character hit
    public boolean moving, statsShown;
    private double animation_state;
    private final String base_char = "elf_m";
    public Graphics gStats;
    public int[] equipment; // 1 is hat, 2 is t-shirt, 3 is sword, 4 is shoes, 5 is ring, 6 is necklace, 7 is belt
    public Image[][] inventoryImages;
    public Image empty;

    // Ingame stats
    private int maxHealth, health, attackdamage, abilitypower, armor, level, exp, gold;

    public Player(Map map, int x, int y) {
        // setup player stats
        this.map = map;
        this.x = x;
        this.y = y;
        this.directions = new boolean[4];
        this.img = new Image[2][4];
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/tiles/" + this.base_char + "_idle_anim_f" + i + ".png")).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 4; i++) {
                img[1][i] = ImageIO.read(new File("res/tiles/" + this.base_char + "_run_anim_f" + i + ".png")).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
            }
            // read and assign image for hit animation to attribute hitImage
            hitImage = ImageIO.read(new File("res/tiles/" + this.base_char + "_hit_anim_f0.png")).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = img[0][0].getWidth(null);
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

    public void setHit(boolean hit){
        this.hit = hit;
    }

    public void paint(Graphics g) {

        // paint player
        g.setColor(Color.BLACK);
        if (getHit()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
            g.drawImage(hitImage, (int) this.x, (int) this.y, null); // set player's animation to hit animation
        } else if (moving) {
            g.drawImage(img[1][(int) this.animation_state], (int) this.x, (int) this.y, null);
        } else {
            g.drawImage(img[0][(int) this.animation_state], (int) this.x, (int) this.y, null);
        }

        // paint player stats
        if (statsShown) {

            Color myColor = new Color(56, 56, 56, 165);
            Font titleF = new Font("SansSerif", Font.BOLD, 25);
            Font statsF = new Font("SansSerif", Font.PLAIN, 15);
            gStats.drawRect(150, 50, 660, 440);
            gStats.setColor(myColor);
            gStats.fillRect(150, 50, 660, 440);
            gStats.setFont(titleF);
            gStats.setColor(Color.white);
            gStats.drawString("Profil", 260, 80);
            gStats.drawString("Inventar", 570, 80);
            gStats.setFont(statsF);
            gStats.drawString("Leben............................(" + health + "/" + maxHealth + ")", 200, 130);
            gStats.drawString("Angriffsschaden....................." + attackdamage, 200, 160);
            gStats.drawString("Fähigkeitsstärke....................." + abilitypower, 200, 190);
            gStats.drawString("Rüstung..................................." + armor, 200, 220);
            gStats.drawString("Gold........................................" + gold, 200, 330);
            gStats.drawString("Level............." + level + "(" + exp + " XP/" + 200 + " XP)", 200, 360); // needs formula for maxXP

            if (moving) {
                gStats.drawImage((img[1][(int) this.animation_state]).getScaledInstance(120, -1, Image.SCALE_DEFAULT), 560, 110, null);
            } else {
                gStats.drawImage((img[0][(int) this.animation_state]).getScaledInstance(120, -1, Image.SCALE_DEFAULT), 560, 110, null);
            }
            gStats.drawImage(inventoryImages[0][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 100, null);
            gStats.drawImage(inventoryImages[1][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 180, null);
            gStats.drawImage(inventoryImages[2][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 255, null);
            gStats.drawImage(inventoryImages[3][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 330, null);
            gStats.drawImage(inventoryImages[4][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 155, null);
            gStats.drawImage(inventoryImages[5][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 230, null);
            gStats.drawImage(inventoryImages[6][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 305, null);
            gStats.drawImage(inventoryImages[6][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 305, null);
            gStats.drawImage(empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 420, null);
            gStats.drawImage(empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 561, 420, null);
            gStats.drawImage(empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 622, 420, null);
            gStats.drawImage(empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 683, 420, null);


        }
    }

    public void update(int time) {
        // update player graphic stats
        if (moving) {
            this.animation_state += (float) time / 100;
        } else {
            this.animation_state += (float) time / 150;
        }
        this.animation_state %= 4;

        overlap(map.monster);

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
    public boolean overlap(lolz.Monster monster){
        for (int i : new int[]{0, monster.getWidth()}) { //checking for the left and right border of the monster's image
            for (int j : new int[]{0, monster.getWidth()} ) { //checking for the top and bottom border of the monster's image
                if(this.x <= monster.getX() + i && monster.getX() + i <= this.x + this.width && this.y <= monster.getY() + j && monster.getY() + j <= this.y + this.height){ //if any of the monster's boundaries can be found between any of the hero's boundaries, they touch
                    System.out.println("Overlap");
                    return true;
                }
            }
        }
        return false;
    }

}

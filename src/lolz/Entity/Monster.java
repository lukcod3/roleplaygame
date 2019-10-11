package lolz.Entity;

import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Monster extends Entity {
    private double animation_state;
    private int exp, monsterNumber;
    private boolean isHitting, isMoving, turnedRight;

    public Monster(int x, int y, int maxHealth, int damage, int armor, int exp, int monsterNumber) {
        super(x, y, maxHealth, damage, armor, 10);
        this.exp = exp;
        this.monsterNumber = monsterNumber;

        loadImages(this.monsterNumber);

        // this.width = img[0][0].getWidth(null);
        this.width = Main.VIRTUAL_ENTITY_WIDTH;
        this.height = img[0][0].getHeight(null);
        this.y -= this.getHeight();
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    /*
    public void paint(Graphics g) {
        // System.out.println(map.get_tile_at((int) (this.x), (int) (this.y + this.height)).toString());
        // paint player
        g.setColor(Color.BLACK);
        if (turnedRight) {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
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
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
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
    */

    public void paint(Graphics g) {
        // paint player

        double k = 1.2;
        g.setColor(Color.BLACK);
        if (!turnedRight) {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (int) ((k*img[2][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[2][(int) this.animation_state], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (int) ((k*img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[1][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (int) ((k*img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[0][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            }
        } else {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                //int offset = (int) ((1*img[2][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[2][(int) this.animation_state], g, (int) this.x, (int) this.y);
            } else if (isMoving) {
                //int offset = (int) ((1*img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[1][(int) this.animation_state], g, (int) this.x, (int) this.y);
            } else {
                //int offset = (int) ((1*img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[0][(int) this.animation_state], g, (int) this.x, (int) this.y);
            }
        }
    }

    public void update(int time) {
        // update monster graphic stats
        switch(this.monsterNumber) {
            case 0: // Ghoul

            case 2: // Undead Warrior
                updateAnimationState(time, 4, 6, 5);
            break;

            case 1: // Imp
                updateAnimationState(time, 5, 5, 10);
            break;

            case 3: // Executioner
                updateAnimationState(time, 4, 6, 6);
            break;

            case 4: // Fire Golem
                updateAnimationState(time, 5, 6, 7);
            break;

            default: // Big Demon
                updateAnimationState(time, 4, 4, 4);
            break;
        }
    }

    private void loadImages(int monsterNumber) {
        this.img = new Image[3][];
        switch(monsterNumber) {
            case 0: // Ghoul
                this.img[0] = new Image[4];
                this.img[1] = new Image[6];
                this.img[2] = new Image[5];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Ghoul/Individual Sprites/ghoul-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Ghoul/Individual Sprites/ghoul-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 5; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Ghoul/Individual Sprites/ghoul-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            break;

            case 1: // Imp
                this.img[0] = new Image[5];
                this.img[1] = new Image[5];
                this.img[2] = new Image[10];
                try {
                    for (int i = 0; i < 5; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Imp/Individual Sprites/imp-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 5; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Imp/Individual Sprites/imp-move-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 10; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Imp/Individual Sprites/imp-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            break;

            case 2: // Undead Warrior
                this.img[0] = new Image[4];
                this.img[1] = new Image[6];
                this.img[2] = new Image[5];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Undead Warrior/Individual Sprites/undead-warrior-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Undead Warrior/Individual Sprites/undead-warrior-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 5; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Undead Warrior/Individual Sprites/undead-warrior-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            break;

            case 3: // Executioner
                this.img[0] = new Image[4];
                this.img[1] = new Image[6];
                this.img[2] = new Image[6];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Executioner/Individual Sprites/executioner-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Executioner/Individual Sprites/executioner-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Executioner/Individual Sprites/executioner-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            break;

            case 4: // Fire Golem
                this.img[0] = new Image[5];
                this.img[1] = new Image[6];
                this.img[2] = new Image[7];
                try {
                    for (int i = 0; i < 5; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Fire Golem/Individual Sprites/fire-golem-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Fire Golem/Individual Sprites/fire-golem-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 7; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Fire Golem/Individual Sprites/fire-golem-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            break;

            default: // Big Demon
                this.img = new Image[2][4];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/tiles/big_demon_idle_anim_f" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 4; i++) {
                        img[1][i] = ImageIO.read(new File("res/tiles/big_demon_run_anim_f" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    private void setHitting(boolean hitting) {
        isHitting = hitting;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    private void updateAnimationState(int time, int idle, int move, int hit) {
        if (isHitting) {
            this.animation_state += (float) time / 100;
            if (this.animation_state > hit) {
                this.setHitting(false);
            }
        } else if (isMoving) {
            this.animation_state += (float) time / 100;
            this.animation_state %= move;
        } else {
            this.animation_state += (float) time / 150;
            this.animation_state %= idle;
        }
    }
}

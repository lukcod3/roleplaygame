package lolz.Entity;

import lolz.Main;

import java.awt.*;

public abstract class Entity {
    public double x, y;
    public Image[][] img;
    public int width, height;
    public int maxHealth, health, damage, armor;
    double speed;
    public double animation_state;
    public boolean turnedRight, isMoving;
    boolean isHitting;

    Entity(int x, int y, int maxHealth, int damage, int armor, double speed) {
        this.x = x;
        this.y = y;
        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDamage(damage);
        this.setArmor(armor);
        this.speed = speed;
        this.animation_state = Math.random() * 10;
    }

    public abstract void paint(Graphics g);

    public void paint(Graphics g, double k) {
        // paint player

        g.setColor(Color.BLACK);
        if (!turnedRight) {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (int) ((k * img[2][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[2][(int) this.animation_state], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (int) ((k * img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[1][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (int) ((k * img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
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

    public abstract void update(int time);

    //Getters and Setters
    public int getMaxHealth() {
        return maxHealth;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        if (health >= 0 && health <= this.getMaxHealth()) {
            this.health = health;
        } else {
            this.health = 0;
        }
    }

    private void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    private void setDamage(int attackdamage) {
        this.damage = attackdamage;
    }

    public int getArmor() {
        return armor;
    }

    private void setArmor(int armor) {
        this.armor = armor;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}

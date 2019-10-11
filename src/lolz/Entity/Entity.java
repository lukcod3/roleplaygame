package lolz.Entity;

import java.awt.*;

public abstract class Entity {
    public double x, y;
    public Image[][] img;
    int width, height;
    public int maxHealth, health, damage, armor;
    double speed;

    Entity(int x, int y, int maxHealth, int damage, int armor, double speed) {
        this.x = x;
        this.y = y;
        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDamage(damage);
        this.setArmor(armor);
        this.speed = speed;
    }

    public abstract void paint(Graphics g);

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

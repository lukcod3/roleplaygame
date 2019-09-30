package lolz.Entity;

import java.awt.*;

public abstract class Entity {
    public double x, y;
    int width, height;
    private int maxHealth, health, damage, armor;
    public double speed;

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

    public int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
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

    int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}

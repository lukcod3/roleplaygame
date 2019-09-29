package lolz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Monster {
    private double x, y;
    private int width, height;
    private Image[][] img;
    private double animation_state;
    private final String base_monster = "big_demon";
    private int maxHealth, health, attackdamage, armor;

    public Monster(int x, int y, int maxHealth, int attackdamage, int armor){
        this.x = x;
        this.y = y;
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
        setAttackdamage(attackdamage);
        setArmor(armor);
        this.img = new Image[2][4];
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/tiles/" + this.base_monster + "_idle_anim_f" + Integer.toString(i) + ".png")).getScaledInstance(50, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 4; i++) {
                img[1][i] = ImageIO.read(new File("res/tiles/" + this.base_monster + "_run_anim_f" + Integer.toString(i) + ".png")).getScaledInstance(50, -1, Image.SCALE_SMOOTH);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.width = img[0][0].getWidth(null);
        this.height = img[0][0].getHeight(null);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void paint(Graphics g) {
        // paint monster
        g.setColor(Color.BLACK);
        g.drawImage(img[0][(int)this.animation_state], (int) this.x, (int) this.y, null);
    }

    public void update(int time){
        // update monster graphic stats
        this.animation_state += (float) time / 150;
        this.animation_state %= 4;
    }

    //Getters and Setters
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getAttackdamage() {
        return attackdamage;
    }

    public void setAttackdamage(int attackdamage) {
        this.attackdamage = attackdamage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}

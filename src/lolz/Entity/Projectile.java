package lolz.Entity;

import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class Projectile {

    private int x, y;
    private double ix, iy;
    private Image img[];
    private turnNumber turnNumber;
    private AffineTransform at;
    private Graphics2D g2d;

    private enum turnNumber {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST}

    public Projectile(int x, int y, turnNumber turnNumber) {

        setX(x);
        setY(y);
        setTurnNumber(turnNumber);
        this.at = AffineTransform.getTranslateInstance(getX(), getY());

        this.img = new Image[3];

        if (turnNumber == turnNumber.EAST || turnNumber == turnNumber.NORTHEAST || turnNumber == turnNumber.SOUTHEAST) {
            try {
                for (int i = 0; i < 3; i++) {
                    img[i] = ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + "-inverted.png")).getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                for (int i = 0; i < 3; i++) {
                    img[i] = ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + ".png")).getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setIx(img[0].getWidth(null) / 2);
        setIy(img[0].getHeight(null) / 2);
    }

    // Getter & Setter
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public turnNumber getTurnNumber() {
        return this.turnNumber;
    }

    public void setTurnNumber(turnNumber turnNumber) {
        this.turnNumber = turnNumber;
    }

    public double getIx() {
        return this.ix;
    }

    public void setIx(double ix) {
        this.ix = ix;
    }

    public double getIy() {
        return this.iy;
    }

    public void setIy(double iy) {
        this.iy = iy;
    }


    public void paint() {

    }

    public void update() {

    }

    private void drawRotatedImage(Graphics g, Image img) {
        this.g2d = (Graphics2D) g;
        switch (getTurnNumber()) {
            case NORTH:
                this.at.rotate(Math.toRadians(90));
                this.at.translate(-getIx(), -getIy());
                this.g2d.drawImage(img, this.at, null);
            break;

            case NORTHEAST:

            case SOUTHWEST:
                this.at.rotate(Math.toRadians(-45));
                this.at.translate(-getIx(), -getIy());
                this.g2d.drawImage(img, this.at, null);
            break;

            case EAST:

            case WEST:
                g.drawImage(img, getX(), getY(), null);
            break;

            case SOUTHEAST:

            case NORTHWEST:
                this.at.rotate(Math.toRadians(45));
                this.at.translate(-getIx(), -getIy());
                this.g2d.drawImage(img, this.at, null);
            break;

            case SOUTH:
                this.at.rotate(Math.toRadians(-90));
                this.at.translate(-getIx(), -getIy());
                this.g2d.drawImage(img, this.at, null);
            break;
        }
    }

}

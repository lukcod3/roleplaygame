package lolz.Entity;

import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class Projectile {

    private double x, y;
    private double ix, iy, animation_state;
    private Image img[];
    private turnNumber turnNumber;
    private AffineTransform at;
    private Graphics2D g2d;

    private enum turnNumber {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST}

    public Projectile(double x, double y, turnNumber turnNumber) {

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
    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
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


    public void paint(Graphics g) {
        drawRotatedImage(g, img[(int) animation_state]);
    }

    public void update(int time) {
        this.animation_state += (float) time / 100;
        this.animation_state %= 3;

        double movement = 0.25 * time;

        switch (getTurnNumber()) {
            case NORTH:
                setY(getY() - movement);
            break;

            case NORTHEAST:
                movement *= 1.0 / Math.pow(2, 0.5);
                setY(getY() - movement);
                setX(getX() + movement);
            break;

            case EAST:
                setX(getX() + movement);
            break;

            case SOUTHEAST:
                movement *= 1.0 / Math.pow(2, 0.5);
                setY(getY() + movement);
                setX(getX() + movement);
            break;

            case SOUTH:
                setY(getY() + movement);
            break;

            case SOUTHWEST:
                movement *= 1.0 / Math.pow(2, 0.5);
                setY(getY() + movement);
                setX(getX() - movement);
            break;

            case WEST:
                setX(getX() - movement);
            break;

            case NORTHWEST:
                movement *= 1.0 / Math.pow(2, 0.5);
                setY(getY() - movement);
                setX(getX() - movement);
            break;
        }

        setX(getX() + 0.25 * time);
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
                this.at.rotate(Math.toRadians(0));
                this.at.translate(-getIx(), -getIy());
                this.g2d.drawImage(img, this.at, null);
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

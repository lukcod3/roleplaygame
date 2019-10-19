package lolz.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class Projectile {

    private double x, y;
    private double ix, iy, animation_state;
    private Image[] img;
    private TurnNumber turnNumber;
    private AffineTransform at;
    private Graphics2D g2d;
    private double boxLowX, boxHighX, boxLowY, boxHighY;

    public enum TurnNumber {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST}

    public Projectile(double x, double y, TurnNumber turnNumber, float[] rgba) {

        setX(x);
        setY(y);
        setTurnNumber(turnNumber);
        this.at = AffineTransform.getTranslateInstance(getX(), getY());
        this.at.translate(getIx(), getIy());

        this.img = new Image[3];

        if (getTurnNumber() == TurnNumber.EAST || getTurnNumber() == TurnNumber.NORTHEAST || getTurnNumber() == TurnNumber.SOUTHEAST) {
            try {
                if (rgba == null) {
                    for (int i = 0; i < 3; i++) {
                        img[i] = ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + "-inverted.png")).getScaledInstance(-1, 15, Image.SCALE_SMOOTH);
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        img[i] = Entity.tint(rgba[0], rgba[1], rgba[2], rgba[3], ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + "-inverted.png"))).getScaledInstance(-1, 15, Image.SCALE_SMOOTH);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (rgba == null) {
                    for (int i = 0; i < 3; i++) {
                        img[i] = ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + ".png")).getScaledInstance(-1, 15, Image.SCALE_SMOOTH);
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        img[i] = Entity.tint(rgba[0], rgba[1], rgba[2], rgba[3], ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + ".png"))).getScaledInstance(-1, 15, Image.SCALE_SMOOTH);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (getTurnNumber() == TurnNumber.NORTH || getTurnNumber() == TurnNumber.SOUTH) {
            setIx(img[0].getHeight(null) / 2.0);
            setIy(img[0].getWidth(null) / 2.0);
        } else {
            setIx(img[0].getWidth(null) / 2.0);
            setIy(img[0].getHeight(null) / 2.0);
        }

        setX(getX() + getIx());
        setY(getY() + getIy());

        setHitBox();

        // update before being drawn
        this.update(1);

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

    private TurnNumber getTurnNumber() {
        return this.turnNumber;
    }

    private void setTurnNumber(TurnNumber turnNumber) {
        this.turnNumber = turnNumber;
    }

    public double getIx() {
        return this.ix;
    }

    private void setIx(double ix) {
        this.ix = ix;
    }

    public double getIy() {
        return this.iy;
    }

    private void setIy(double iy) {
        this.iy = iy;
    }

    public double getBoxLowX() {
        return this.boxLowX;
    }

    private void setBoxLowX(double boxLowX) {
        this.boxLowX = boxLowX;
    }

    public double getBoxHighX() {
        return this.boxHighX;
    }

    private void setBoxHighX(double boxHighX) {
        this.boxHighX = boxHighX;
    }

    public double getBoxLowY() {
        return this.boxLowY;
    }

    private void setBoxLowY(double boxLowY) {
        this.boxLowY = boxLowY;
    }

    public double getBoxHighY() {
        return this.boxHighY;
    }

    private void setBoxHighY(double boxHighY) {
        this.boxHighY = boxHighY;
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
        this.at = AffineTransform.getTranslateInstance(getX(), getY());
        setHitBox();
    }

    private void drawRotatedImage(Graphics g, Image img) {
        this.g2d = (Graphics2D) g;
        switch (getTurnNumber()) {
            case NORTH:
                this.at.rotate(Math.toRadians(90));
                this.g2d.drawImage(img, this.at, null);
                break;

            case NORTHEAST:

            case SOUTHWEST:
                this.at.rotate(Math.toRadians(-45));
                this.g2d.drawImage(img, this.at, null);
                break;

            case EAST:

            case WEST:
                this.at.rotate(Math.toRadians(0));
                this.g2d.drawImage(img, this.at, null);
                break;

            case SOUTHEAST:

            case NORTHWEST:
                this.at.rotate(Math.toRadians(45));
                this.g2d.drawImage(img, this.at, null);
                break;

            case SOUTH:
                this.at.rotate(Math.toRadians(-90));
                this.g2d.drawImage(img, this.at, null);
                break;
        }
    }

    private void setHitBox() {
        switch (getTurnNumber()) {
            case NORTH:
                setBoxLowX(this.getX() - this.getIx() * 2);
                setBoxHighX(this.getX()); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY());
                setBoxHighY(this.getY() + this.getIy()); // weiter unten als getBoxLowY()
                break;

            case NORTHEAST:
                setBoxLowX(this.getX() + Math.cos(45) * getIx() * 2);
                setBoxHighX(this.getX() + Math.cos(45) * getIx() * 4); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY() - Math.sin(45) * this.getIx() * 2);
                setBoxHighY(this.getY() - Math.sin(45) * this.getIx()); // weiter unten als getBoxLowY()
                break;

            case EAST:
                setBoxLowX(this.getX() + this.getIx());
                setBoxHighX(this.getX() + this.getIx() * 2); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY());
                setBoxHighY(this.getY() + this.getIy() * 2); // weiter unten als getBoxLowY()
                break;

            case SOUTHEAST:
                setBoxLowX(this.getX() + Math.cos(45) * this.getIx());
                setBoxHighX(this.getX() + Math.cos(45) * this.getIx() * 2); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY() + Math.sin(45) * this.getIx());
                setBoxHighY(this.getY() + Math.sin(45) * this.getIx() * 2); // weiter unten als getBoxLowY()
                break;

            case SOUTH:
                setBoxLowX(this.getX());
                setBoxHighX(this.getX() + this.getIx() * 2); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY() - this.getIy());
                setBoxHighY(this.getY()); // weiter unten als getBoxLowY()
                break;

            case SOUTHWEST:
                setBoxLowX(this.getX());
                setBoxHighX(this.getX() + Math.sin(45) * this.getIx()); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY() - Math.cos(45) * this.getIx());
                setBoxHighY(this.getY()); // weiter unten als getBoxLowY()
                break;

            case WEST:
                setBoxLowX(this.getX());
                setBoxHighX(this.getX() + this.getIx()); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY());
                setBoxHighY(this.getY() + this.getIy() * 2); // weiter unten als getBoxLowY()
                break;

            case NORTHWEST:
                setBoxLowX(this.getX() - this.getIy() * 2);
                setBoxHighX(this.getX() + this.getIy() * 2); // weiter rechts als getBoxLowX()
                setBoxLowY(this.getY());
                setBoxHighY(this.getY() + Math.sin(45) * this.getIx()); // weiter unten als getBoxLowY()
                break;
        }
    }

    // check if the projectile is touching a given entity
    public boolean overlap(Entity entity) {
        for (double i : new double[]{getBoxLowX(), getBoxHighX()}) {
            for (double j : new double[]{getBoxLowY(), getBoxHighY()}) {
                if (entity.getX() <= i && i <= entity.getX() + entity.getWidth() && entity.getY() - entity.getHeight() <= j && j <= entity.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

}

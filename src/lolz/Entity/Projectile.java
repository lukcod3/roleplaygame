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

    public enum TurnNumber {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST}

    public Projectile(double x, double y, TurnNumber turnNumber) {

        setX(x);
        setY(y);
        setTurnNumber(turnNumber);
        this.at = AffineTransform.getTranslateInstance(getX(), getY());
        this.at.translate(getIx(), getIy());

        this.img = new Image[3];

        if (getTurnNumber() == TurnNumber.EAST || getTurnNumber() == TurnNumber.NORTHEAST || getTurnNumber() == TurnNumber.SOUTHEAST) {
            try {
                for (int i = 0; i < 3; i++) {
                    img[i] = ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + "-inverted.png")).getScaledInstance(-1, 15, Image.SCALE_SMOOTH);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                for (int i = 0; i < 3; i++) {
                    img[i] = ImageIO.read(new File("res/monster/Necromancer/projectile/necromancer-projectile-projectile-0" + i + ".png")).getScaledInstance(-1, 15, Image.SCALE_SMOOTH);
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

    TurnNumber getTurnNumber() {
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


    public void paint(Graphics g) {
        drawRotatedImage(g, img[(int) animation_state]);
        g.setColor(Color.WHITE);
        g.drawRoundRect((int) this.getX(), (int) this.getY(), 5, 5, 50, 50);
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

    // check if any given entity is "touching" the projectile or rather if the projectile is touching it
    public boolean overlap(Entity entity) {
        for (int i : new int[]{0, entity.getWidth()}) { //checking for the left and right border of the entity's image
            for (int j : new int[]{0, entity.getHeight()}) { //checking for the top and bottom border of the entity's image
                switch (getTurnNumber()) {
                    case NORTH:
                        if ((this.getX() - this.getIx() * 2 <= entity.getX() + i) && (entity.getX() + i <= this.getX()) && (this.getY() <= entity.getY() + j) && (entity.getY() + j <= this.getY() + this.getIy())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case NORTHEAST:
                        if ((this.getX() + Math.cos(45) * getIx() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + Math.cos(45) * getIx() * 2) && (this.getY() - Math.sin(45) * this.getIx() * 2 <= entity.getY() + j) && (entity.getY() + j <= this.getY() - Math.sin(45) * this.getIx())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case EAST:
                        if ((this.getX() + this.getIx() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getIx() * 2) && (this.getY() - this.getIy() * 2 <= entity.getY() + j) && (entity.getY() + j <= this.getY())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case SOUTHEAST:
                        if ((this.getX() + Math.cos(45) * this.getIx() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + Math.cos(45) * this.getIx() * 2) && (this.getY() + Math.sin(45) * this.getIy() <= entity.getY() + j) && (entity.getY() + j <= this.getY() + Math.sin(45) * this.getIy() * 2)) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case SOUTH:
                        if ((this.getX() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getIx() * 2) && (this.getY() - this.getIy() <= entity.getY() + j) && (entity.getY() + j <= this.getY())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case SOUTHWEST:
                        if ((this.getX() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + Math.sin(45) * this.getIx()) && (this.getY() - Math.cos(45) * this.getIy() <= entity.getY() + j) && (entity.getY() + j <= this.getY())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case WEST:
                        if ((this.getX() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getIx()) && (this.getY() <= entity.getY() + j) && (entity.getY() + j <= this.getY() + this.getIy() * 2)) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;

                    case NORTHWEST:
                        if ((this.getX() - this.getIy() * 2 <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getIy() * 2) && (this.getY() <= entity.getY() + j) && (entity.getY() + j <= this.getY() + Math.sin(45) * this.getIy())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }

}

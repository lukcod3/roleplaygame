package lolz.Maps;

import lolz.Entity.*;
import lolz.GUI.Tile.StaticTile;
import lolz.GUI.Walker;
import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class RandomMap extends Map {

    BufferedImage[] portal;
    public double portalState;

    public RandomMap() {
        // setup map
        super(20000, 15000);

        // generate map
        generateMap();
        portal = new BufferedImage[8];
        portalState = 0;

        // spawn player
        this.player = new Mage(this, (this.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE, (this.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE);

        // update expFactor before new map is created
        this.expFactor = Math.pow(1.2, this.player.level);

        //spawn random monsters
        spawnRandomMonsters(this.tiles);

        // set entities array
        this.entities.add(this.player);
        this.monsterCount += 1;
        try {
            for (int j = 0; j < 8; j++) {
                portal[j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 64, 64, 64);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private int numberOfTiles() {
        int n = 0;
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                if (this.tiles[y][x].contains(StaticTile.FLOOR_1)) {
                    n++;
                }
            }
        }
        return n;
    }

    private void generateMap() {
        // set parameters
        double PERCENTAGE_TO_FILL = 0.5;
        int NUMBER_OF_START_WALKERS = 1;
        int NUMBER_OF_MAX_WALKERS = 10;
        double WALKER_DELETION_POSS = 0.05;
        double WALKER_SPAWNING_POSS = 0.05;
        double WALKER_CHANGE_DIR_POSS = 0.2;
        double ROOM_POSS = 0.01;
        double ROOM_MIN = 5;
        double ROOM_MAX = 10;

        // initialize walkers
        ArrayList<Walker> walkers = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_START_WALKERS; i++) {
            walkers.add(new Walker(this, this.VIRTUAL_WIDTH / 2, this.VIRTUAL_HEIGHT / 2));
        }

        // generate map
        int i = 0;
        while (PERCENTAGE_TO_FILL * this.VIRTUAL_AREA > numberOfTiles() && i < 100000) {
            // let walkers change direction, walk and spawn new walkers
            int size = walkers.size();
            for (int j = 0; j < size; j++) {
                Walker w = walkers.get(j);

                // change direction
                if (Math.random() < WALKER_CHANGE_DIR_POSS) {
                    w.changeDirection();
                }

                // move walker
                w.move();

                // generate room
                if (Math.random() < ROOM_POSS) {
                    int x = (int) (Math.random() * (ROOM_MAX - ROOM_MIN) + ROOM_MIN);
                    int y = (int) (Math.random() * (ROOM_MAX - ROOM_MIN) + ROOM_MIN);
                    for (int x0 = w.x - x / 2; x0 < w.x - x / 2 + x; x0++) {
                        for (int y0 = w.y - y / 2; y0 < w.y - y / 2 + y; y0++) {
                            if (0 <= x0 && x0 < this.VIRTUAL_WIDTH && 0 <= y0 && y0 < this.VIRTUAL_HEIGHT) {
                                this.tiles[y0][x0].add(StaticTile.FLOOR_1);
                            }
                        }
                    }
                }

                // spawn new walkers
                if (Math.random() < WALKER_SPAWNING_POSS && walkers.size() < NUMBER_OF_MAX_WALKERS) {
                    walkers.add(new Walker(this, w.x, w.y));
                }
            }

            // delete walkers
            for (int j = walkers.size() - 1; j >= 0; j--) {
                if (Math.random() < WALKER_DELETION_POSS && walkers.size() > 1) {
                    walkers.remove(j);
                }
            }

            i++;
        }

        // generate walls
        this.makeWalls();

    }

    @Override
    public void update(int time) {
        portalState += 0.1;

        this.player.update(time);

        // let the mage shoot his projectile
        if (this.player instanceof Mage) {
            if (this.player.getHitting() && (int) (this.player.animation_state % 5) == 2) {
                if (!this.player.isMoving) {
                    if (this.player.turnedRight) {
                        projectiles.add(new Projectile(this.player.getX() + this.player.getWidth(), this.player.getY() + this.player.getHeight() / 2.0, Projectile.TurnNumber.EAST));
                    } else {
                        projectiles.add(new Projectile(this.player.getX(), this.player.getY() + this.player.getHeight() / 2.0, Projectile.TurnNumber.WEST));
                    }
                } else if (1 == 1 /* check which directions[] are active and decide which TurnNumber to use accordingly*/) {

                }
            }
        } else {
            // let the fighter attack monsters he overlaps with
            for (Entity entity : this.entities) {
                if (!(entity instanceof Player) && this.player.overlap(entity)) {
                    // if the attacked monster is dead and their index in the entities ArrayList to the removeEntities array and increase the index that tells you how many monsters have to be removed (removeIndex) by one
                    if (this.player.attack(entity)) {
                        this.removeEntities[this.removeIndex] = this.entities.indexOf(entity);
                        this.removeIndex += 1;
                    }
                }
            }
        }

        // remove the monsters that are dead (health == 0) from the entities ArrayList and their index from the removeEntities array and decrease the index telling you how many monsters have to be deleted as well as the monsterCount by one
        if (this.removeIndex != 0) {
            int index = this.removeIndex - 1;
            for (int i = index; i >= 0; i--) {
                this.entities.remove(this.removeEntities[i]);
                this.removeEntities[i] = 0;
                this.removeIndex -= 1;
                this.monsterCount -= 1;
            }
        }
        // update all monsters
        for (Entity entity : this.entities) {
            if (entity instanceof Monster) {
                entity.update(time);
                // if monster in range of player it will follow him
                if (!this.followingMonsters.contains(entity) && Math.pow(Math.pow(entity.getX() - player.getX(), 2) + Math.pow(entity.getY() - player.getY(), 2), 0.5) < 5) {
                    this.followingMonsters.add((Monster) entity);
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (portalState < 8) {
            player.allowedToMove = false;
            g.drawImage(portal[((int) portalState)].getScaledInstance(120, -1, Image.SCALE_DEFAULT), 410, 240, null);
        } else {
            player.allowedToMove = true;
        }
    }
}

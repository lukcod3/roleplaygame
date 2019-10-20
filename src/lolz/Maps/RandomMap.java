package lolz.Maps;

import lolz.Entity.*;
import lolz.GUI.Item;
import lolz.GUI.Tile.StaticTile;
import lolz.GUI.Walker;
import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class RandomMap extends Map {

    private Image[][] portal;
    private double portalState;
    private boolean dead, playerPortChanneled;
    private int[] oldPlayerCoordinates;


    public RandomMap(Player player) {
        // setup map
        super(20000, 15000);


        // generate map
        generateMap();
        portal = new Image[3][8];
        portalState = 0;
        this.playerPortChanneled = false;

        // spawn player
        //this.player = new Mage(this, (this.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE, (this.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE);
        this.player = player;
        this.player.map = this;
        this.player.x = (this.VIRTUAL_WIDTH / 2.0) * Main.TILE_SIZE;
        this.player.y = (this.VIRTUAL_HEIGHT / 2.0) * Main.TILE_SIZE;
        this.player.directions = new boolean[4];
        oldPlayerCoordinates = new int[2];
        // update expFactor before new map is created
        this.expFactor = Math.pow(1.2, this.player.level);

        //spawn random monsters
        spawnRandomMonsters(this.tiles);

        // set entities array
        this.entities.add(this.player);
        // load portal image
        try {
            for (int j = 0; j < 8; j++) {
                portal[0][j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 0, 64, 64).getScaledInstance(120, -1, Image.SCALE_DEFAULT);
            }
            for (int j = 0; j < 8; j++) {
                portal[1][j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 64, 64, 64).getScaledInstance(120, -1, Image.SCALE_DEFAULT);
            }
            for (int j = 0; j < 5; j++) {
                portal[2][j] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(j * 64, 64, 64, 64).getScaledInstance(120, -1, Image.SCALE_DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean getDead() {
        return dead;
    }
    public void setDead(boolean x){
        dead = x;
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

        if (this.player.getHealth() == 0){
            setDead(true);
        }

        if (getDead() == true) {
            player.goBack = true;
            player.readyToPort = false;
            player.backport = false;
            player.inventory.equipment = new Item[11];
            this.player.oldCoordinates[0] = (int) this.player.x;
            this.player.oldCoordinates[1] = (int) this.player.y;
            player.setLevel(1);
            player.setGold(0);
            player.setExp(0);

        }
        // let the mage shoot his projectile
        if (this.player instanceof Mage) {

            if (((Mage) this.player).hasDamaged && (int) (this.player.animation_state % 5) != 2) {
                ((Mage) this.player).hasDamaged = false;
            }

            if (this.player.getHitting() && (int) (this.player.animation_state % 5) == 2 && !((Mage) this.player).hasDamaged) {
                ((Mage) this.player).hasDamaged = true;
                if (!this.player.isMoving) {
                    if (this.player.turnedRight) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 3.0, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.EAST, Main.rgba_projectiles));
                    } else {
                        this.projectiles.add(new Projectile(this.player.getX() - this.player.getWidth() * 1.5, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.WEST, Main.rgba_projectiles));
                    }
                } else if (this.player.directions[0] || this.player.directions[1] || this.player.directions[2] || this.player.directions[3]) {
                    if (this.player.directions[0] && this.player.directions[3]) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 6.0, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.NORTHEAST, Main.rgba_projectiles));
                    } else if (this.player.directions[2] && this.player.directions[3]) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 10.0, this.player.getY() - this.player.getHeight() / 1.75, Projectile.TurnNumber.SOUTHEAST, Main.rgba_projectiles));
                    } else if (this.player.directions[2] && this.player.directions[1]) {
                        this.projectiles.add(new Projectile(this.player.getX() - this.player.getWidth() * 1.5, this.player.getY(), Projectile.TurnNumber.SOUTHWEST, Main.rgba_projectiles));
                    } else if (this.player.directions[0] && this.player.directions[1]) {
                        this.projectiles.add(new Projectile(this.player.getX() - this.player.getWidth() * 1.5, this.player.getY() - this.player.getHeight(), Projectile.TurnNumber.NORTHWEST, Main.rgba_projectiles));
                    } else if (this.player.directions[0]) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 2.0, this.player.getY() - this.player.getHeight() * 1.5, Projectile.TurnNumber.NORTH, Main.rgba_projectiles));
                    } else if (this.player.directions[1]) {
                        this.projectiles.add(new Projectile(this.player.getX() - this.player.getWidth() * 1.5, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.WEST, Main.rgba_projectiles));
                    } else if (this.player.directions[2]) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 10.0, this.player.getY(), Projectile.TurnNumber.SOUTH, Main.rgba_projectiles));
                    } else if (this.player.directions[3]) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 3.0, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.EAST, Main.rgba_projectiles));
                    }
                } else {
                    if (this.player.lastDirection == 1) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 2.0, this.player.getY() - this.player.getHeight() * 1.5, Projectile.TurnNumber.NORTH, Main.rgba_projectiles));
                    } else if (this.player.lastDirection == 2) {
                        this.projectiles.add(new Projectile(this.player.getX() - this.player.getWidth() * 1.5, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.WEST, Main.rgba_projectiles));
                    } else if (this.player.lastDirection == 3) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 10.0, this.player.getY(), Projectile.TurnNumber.SOUTH, Main.rgba_projectiles));
                    } else if (this.player.lastDirection == 4) {
                        this.projectiles.add(new Projectile(this.player.getX() + this.player.getWidth() / 3.0, this.player.getY() - this.player.getHeight() / 2.0, Projectile.TurnNumber.EAST, Main.rgba_projectiles));
                    }
                }
            }

            // update all projectiles
            boolean overlap;
            boolean outOfBounds;
            int removedEntityIndex;
            int removedEntityIndexOld;
            for (Projectile p : this.projectiles) {
                overlap = false;
                outOfBounds = false;
                removedEntityIndex = 0;
                removedEntityIndexOld = -1; // this value doesn't have a specific purpose, only needs to be different from any index of this.entities
                for (int i : new int[]{(int) p.getBoxLowX(), (int) p.getBoxHighX()}) {
                    //System.out.println(get_tile_at(i, (int) (p.getBoxLowY())));
                    if (get_tile_at(i, (int) (p.getBoxLowY())).topTiles.isEmpty() && (get_tile_at(i, (int) (p.getBoxLowY())).isGround() || get_tile_at(i, (int) (p.getBoxLowY())).contains(StaticTile.WALL) || get_tile_at(i, (int) (p.getBoxLowY())).contains(StaticTile.WALL_RIGHT) || get_tile_at(i, (int) (p.getBoxLowY())).contains(StaticTile.WALL_LEFT))) {
                        for (Entity entity : this.entities) {
                            if (entity instanceof Monster && p.overlap(entity)) { // check for every monster if it was hit by a projectile
                                removedEntityIndex = this.entities.indexOf(entity);
                                if (removedEntityIndex != removedEntityIndexOld) { // make sure that it doesn't try to attack / remove the same monster twice because of "for (int i : new int[]{0, (int) p.getIx() * 2}")
                                    overlap = true;
                                    entity.setHealth(entity.getHealth() - this.player.getDamage()); // player attack
                                    //System.out.println(entity.getHealth());
                                    if (entity.getHealth() == 0) { // remove dead monsters
                                        this.removeEntities[this.removeEntityIndex] = removedEntityIndex;
                                        this.removeEntityIndex += 1;
                                    }
                                }
                            }
                        }
                    } else {
                        outOfBounds = true;
                    }
                    removedEntityIndexOld = removedEntityIndex;
                }
                if (!overlap && !outOfBounds) { // only update if projectile didn't hit monster or wall
                    p.update(time);
                } else { // remove projectile if it overlapped with a monster or if it hit a wall
                    this.removeProjectiles[this.removeProjectileIndex] = this.projectiles.indexOf(p);
                    this.removeProjectileIndex += 1;
                }
            }
            //System.out.println(this.projectiles + " ArrayList.size(): " + this.projectiles.size());

            // remove projectiles
            if (this.removeProjectileIndex != 0) {
                int index = this.removeProjectileIndex - 1;
                for (int i = index; i >= 0; i--) {
                    //System.out.println(this.projectiles + " Size in remove: " + this.projectiles.size() + " i: " + i);
                    this.projectiles.remove(this.removeProjectiles[i]);
                    this.removeProjectiles[i] = 0;
                    this.removeProjectileIndex -= 1;
                }
            }
        } else {
            // let the fighter attack monsters he overlaps with
            for (Entity entity : this.entities) {
                if (entity instanceof Monster && this.player.overlap(entity)) {
                    // if the attacked monster is dead and their index in the entities ArrayList to the removeEntities array and increase the index that tells you how many monsters have to be removed (removeIndex) by one
                    if (this.player.attack(entity)) {
                        this.removeEntities[this.removeEntityIndex] = this.entities.indexOf(entity);
                        this.removeEntityIndex += 1;
                    }
                }
            }
        }

        // remove the monsters that are dead (health == 0) from the entities ArrayList and their index from the removeEntities array and decrease the index telling you how many monsters have to be deleted as well as the monsterCount by one
        if (this.removeEntityIndex != 0) {
            int index = this.removeEntityIndex - 1;
            for (int i = index; i >= 0; i--) {
                try {
                    if (this.entities.get(this.removeEntities[i]) != this.player) {
                        this.entities.remove(this.removeEntities[i]);
                        this.monsterCount -= 1;
                    }  // Fehler-Ausgabe: System.out.println("Der Spieler soll nicht gelöscht werden!");

                } catch (Exception ignore) {

                }
                this.removeEntities[i] = 0;
                this.removeEntityIndex -= 1;
                if (monsterCount == 0) {
                    this.player.backport = true;
                    this.player.oldCoordinates[0] = (int) this.player.x;
                    this.player.oldCoordinates[1] = (int) this.player.y;
                }
                this.player.giveXP((int) (Math.random() * 8) + 12);
                this.player.gold += (int) (Math.random() * 2 + 2);
                if (Math.random() > 0.93) {
                    int stufe;

                    if ((int) (1 + Math.random() * 10) * this.player.level > 60) {
                        stufe = 4;
                    } else if ((int) (1 + Math.random() * 10) * this.player.level > 40) {
                        stufe = 3;
                    } else if ((int) (1 + Math.random() * 10) * this.player.level > 15) {
                        stufe = 2;
                    } else {
                        stufe = 1;
                    }
                    for (int j = 0; j <= 3; j++) {
                        if (this.player.inventory.equipment[7 + j] == null) {
                            this.player.inventory.equipment[7 + j] = this.player.inventory.item[(int) (Math.random() * 7)][stufe];
                            break;
                        }
                    }
                }
            }
        }

        // update all monsters
        for (int i = 0; i < this.entities.size(); i++) {
            Entity entity = this.entities.get(i);
            if (entity instanceof Monster) {
                entity.update(time);
                // if monster in range of player it will follow him
                double distance = Math.pow(Math.pow(entity.getVirtualLeftX() - player.getVirtualLeftX(), 2) + Math.pow(entity.getVirtualY() - player.getVirtualY(), 2), 0.5);
                if (!this.followingMonsters.contains(entity) && distance < 5 && distance > 1) {
                    this.followingMonsters.add((Monster) entity);
                    ((Monster) entity).isFollowing = true;
                    ((Monster) entity).makePath();
                } else if (this.followingMonsters.contains(entity) && (distance > 15 || distance < 1)) {
                    ((Monster) entity).isFollowing = false;
                    this.followingMonsters.remove(entity);
                }
                if (entity.overlap(player)) {
                    if (!entity.getHitting()) {
                        entity.setHitting(true);
                    }
                    setDead(entity.attack(player));
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (this.portalState < 8) {
            this.player.allowedToMove = false;
            g.drawImage(portal[1][((int) portalState)], 410, 180, null);
        } else if (!this.playerPortChanneled) {
            player.allowedToMove = true;
            this.playerPortChanneled = true;
        }
    }
}
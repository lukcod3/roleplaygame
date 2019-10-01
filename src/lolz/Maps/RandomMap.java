package lolz.Maps;

import lolz.Entity.Entity;
import lolz.Entity.Monster;
import lolz.Entity.Player;
import lolz.GUI.Tile.StaticTile;
import lolz.GUI.Walker;
import lolz.Main;

import java.util.ArrayList;

public class RandomMap extends Map {

    public RandomMap() {
        // setup map
        super(20000, 15000);

        // generate map
        generateMap();

        // spawn player
        this.player = new Player(this, (this.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE, (this.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE);
        // spawn monster at player's position
        this.monster = new Monster((this.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE, (this.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE, 50, 15, 10, 25);

        // set entities array
        this.entities.add(this.player);
        this.entities.add(this.monster);
        this.removeEntities = new int[this.entities.size()];
        this.monsterCount += 1;
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

        // initialize walkers
        ArrayList<Walker> walkers = new ArrayList<Walker>();
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

        spawnRandomMonsters(this.tiles);
    }

    @Override
    public void update(int time) {
        this.player.update(time);
        for (Entity entity : this.entities) {
            if (!(entity instanceof Player) && this.player.overlap(entity)) {
                if (this.player.attack(entity)) {
                    this.removeEntities[this.removeIndex] = this.entities.indexOf(entity);
                    this.removeIndex += 1;
                }
            }
        }
        if (this.removeIndex != 0) {
            int index = this.removeIndex - 1;
            for (int i = index ; i >= 0; i --) {
                this.entities.remove(this.removeEntities[i]);
                this.removeEntities[i] = 0;
                this.removeIndex -= 1;
                this.monsterCount -= 1;
                System.out.println(this.monsterCount);
            }
        }
        for (Entity entity : this.entities) {
            if (entity instanceof Monster) {
                entity.update(time);
            }
        }
    }
}

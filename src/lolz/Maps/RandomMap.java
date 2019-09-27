package lolz.Maps;

import lolz.GUI.Walker;
import lolz.Main;
import lolz.Player.Player;

import java.util.ArrayList;

public class RandomMap extends Map {

    public RandomMap() {
        // setup map
        super(20000, 15000);

        // generate map
        generateMap();

        // spawn player
        this.player = new Player(this, (this.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE, (this.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE);
    }

    private int numberOfTiles() {
        int n = 0;
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                if (this.tiles[y][x] == Tile.GROUND) {
                    n++;
                }
            }
        }
        return n;
    }

    private void generateMap() {
        // set parameters
        double PERCENTAGE_TO_FILL = 0.5;
        int NUMBER_OF_START_WALKERS = 2;
        int NUMBER_OF_MAX_WALKERS = 10;
        double WALKER_DELETION_POSS = 0.05;
        double WALKER_SPAWNING_POSS = 0.2;
        double WALKER_CHANGE_DIR_POSS = 0.5;

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

        // add walls
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                if (this.tiles[y][x] == Tile.EMPTY) {
                    // check for nearby ground tiles
                    boolean[] ground = new boolean[4]; // 0 -> north, 1 -> east, 2 -> south, 3 -> west
                    if (this.get_tile_at_virtual(x, y - 1) == Tile.GROUND) {
                        ground[0] = true;
                    }
                    if (this.get_tile_at_virtual(x + 1, y) == Tile.GROUND) {
                        ground[1] = true;
                    }
                    if (this.get_tile_at_virtual(x, y + 1) == Tile.GROUND) {
                        ground[2] = true;
                    }
                    if (this.get_tile_at_virtual(x - 1, y) == Tile.GROUND) {
                        ground[3] = true;
                    }

                    int n = 0;
                    for (boolean b : ground) {
                        if (b) {
                            n++;
                        }
                    }

                    if (n == 4) {
                        // remove wall if surrounded by ground tiles
                        this.tiles[y][x] = Tile.GROUND;
                    } else if (n == 1) {
                        for (int j = 0; j < 4; j++) {
                            if (ground[j]) {
                                if (j == 1) {
                                    this.tiles[y][x] = Tile.WALL_LEFT;
                                } else if (j == 3) {
                                    this.tiles[y][x] = Tile.WALL_RIGHT;
                                } else {
                                    this.tiles[y][x] = Tile.WALL;
                                }
                            }
                        }
                    } else if (n > 0) {
                        this.tiles[y][x] = Tile.WALL;
                    }
                }
            }
        }

    }

    @Override
    public void update(int time) {
        this.player.update(time);
    }
}

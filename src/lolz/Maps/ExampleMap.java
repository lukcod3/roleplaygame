package lolz.Maps;

import lolz.Main;
import lolz.Player;

import java.awt.*;

public class ExampleMap extends Map {

    public ExampleMap() {
        this.WIDTH = 600;
        this.HEIGHT = 500;

        // spawn player
        this.player = new Player(this,this.WIDTH/2, this.HEIGHT/2);

        // setup map
        tiles = new Tile[this.HEIGHT / Main.TILE_SIZE][this.WIDTH / Main.TILE_SIZE];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                tiles[y][x] = Tile.GROUND;
            }
        }

        // setting walls
        for (int i = 0; i < tiles.length; i++) {
            tiles[i][0] = Tile.WALL;
        }
        for (int i = 0; i < this.tiles.length; i++) {
            tiles[i][this.tiles[0].length - 1] = Tile.WALL;
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            tiles[0][i] = Tile.WALL;
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            tiles[this.tiles.length - 1][i] = Tile.WALL;
        }
    }

    @Override
    public void update(int time) {
        this.player.update(time);
    }
}

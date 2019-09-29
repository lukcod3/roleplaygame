package lolz.Maps;

import lolz.GUI.Tile;
import lolz.Main;
import lolz.Player.Player;
import lolz.GUI.Tile.StaticTile;

public class ExampleMap extends Map {

    public ExampleMap() {
        // setup map
        super(600, 500);

        // spawn player
        this.player = new Player(this, this.WIDTH / 2, this.HEIGHT / 2);

        // set ground
        tiles = new Tile[this.VIRTUAL_HEIGHT][this.VIRTUAL_WIDTH];
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                tiles[y][x].add(Tile.StaticTile.GROUND);
            }
        }

        // setting walls
        for (int i = 0; i < this.VIRTUAL_HEIGHT; i++) {
            tiles[i][0].add(StaticTile.WALL);
        }
        for (int i = 0; i < this.VIRTUAL_HEIGHT; i++) {
            tiles[i][this.tiles[0].length - 1].add(StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            tiles[0][i].add(StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            tiles[this.VIRTUAL_HEIGHT - 1][i].add(StaticTile.WALL);
        }
    }

    @Override
    public void update(int time) {
        this.player.update(time);
    }
}

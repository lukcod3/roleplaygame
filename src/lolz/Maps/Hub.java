package lolz.Maps;

import lolz.GUI.Tile;
import lolz.Main;
import lolz.Entity.Player;

import java.awt.*;

public class Hub extends Map {

    public Hub() {
        super(600, 500);

        // spawn player
        this.player = new Player(this,this.WIDTH/2, this.HEIGHT/2);

        // setup map
        Tile.StaticTile[][] tiles = new Tile.StaticTile[this.VIRTUAL_HEIGHT][this.VIRTUAL_WIDTH];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                tiles[y][x] = Tile.StaticTile.FLOOR_1;
            }
        }

        // setting walls
        for (int i = 0; i < tiles.length; i++) {
            tiles[i][0] = Tile.StaticTile.WALL;
        }
        for (int i = 0; i < this.tiles.length; i++) {
            tiles[i][this.tiles[0].length - 1] = Tile.StaticTile.WALL;
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            tiles[0][i] = Tile.StaticTile.WALL;
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            tiles[this.tiles.length - 1][i] = Tile.StaticTile.WALL;
        }
    }

    @Override
    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int)-(this.player.x - Main.WIDTH/2), (int)-(this.player.y - Main.HEIGHT/2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                g.drawImage(Tile.tilePics.get(this.tiles[y][x]), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
            }
        }

        // draw player
        this.player.paint(g);
    }

    @Override
    public void update(int time) {
        this.player.update(time);
    }
}
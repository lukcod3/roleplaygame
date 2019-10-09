package lolz.Maps;

import lolz.Entity.Fighter;
import lolz.Entity.Mage;
import lolz.GUI.Tile;
import lolz.Main;
import lolz.Entity.Player;

import java.awt.*;

public class Hub extends Map {

    public Hub() {
        super(600, 500);

        // spawn player
        this.player = new Fighter(this,this.WIDTH/2, this.HEIGHT/2);

        // setup map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                this.tiles[y][x].add(Tile.StaticTile.FLOOR_1);
            }
        }

        // setting walls
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i][0].remove(Tile.StaticTile.FLOOR_1);
            this.tiles[i][0].add(Tile.StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i][this.tiles[0].length - 1].remove(Tile.StaticTile.FLOOR_1);
            this.tiles[i][this.tiles[0].length - 1].add(Tile.StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            this.tiles[0][i].remove(Tile.StaticTile.FLOOR_1);
            this.tiles[0][i].add(Tile.StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            this.tiles[this.tiles.length - 1][i].remove(Tile.StaticTile.FLOOR_1);
            this.tiles[this.tiles.length - 1][i].add(Tile.StaticTile.WALL);
        }
    }

    @Override
    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int) -(this.player.x - Main.WIDTH / 2), (int) -(this.player.y - Main.HEIGHT / 2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (this.tiles[y][x].contains(Tile.StaticTile.FLOOR_1)) {
                    g.drawImage(Tile.tilePics.get(Tile.StaticTile.FLOOR_1), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                } else if (this.tiles[y][x].contains(Tile.StaticTile.WALL)) {
                    g.drawImage(Tile.tilePics.get(Tile.StaticTile.WALL), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                }
            }
        }

        // draw player
        this.player.paint(g);

        // translate back
        g.translate((int) (this.player.x - Main.WIDTH / 2), (int) (this.player.y - Main.HEIGHT / 2));
    }

    @Override
    public void update(int time) {
        this.player.update(time);
    }
}
package lolz.Maps;

import lolz.Main;
import lolz.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class Map {
    public Player player;
    int WIDTH, HEIGHT;

    public enum Tile {GROUND, WALL}
    private HashMap<Tile, Image> tilePics;

    Tile[][] tiles;

    Map() {
        this.tilePics = new HashMap<Tile, Image>();
        try {
            tilePics.put(Tile.GROUND, ImageIO.read(new File("res/tiles/floor_1.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
            tilePics.put(Tile.WALL, ImageIO.read(new File("res/tiles/wall_mid.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int)-(this.player.x - Main.WIDTH/2), (int)-(this.player.y - Main.HEIGHT/2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                g.drawImage(this.tilePics.get(this.tiles[y][x]), Main.TILE_SIZE*x, Main.TILE_SIZE*y, null);
            }
        }

        // draw player
        this.player.paint(g);
    }

    public abstract void update(int time);

    public Tile tile_at(int x, int y) {
        return this.tiles[y / Main.TILE_SIZE][x / Main.TILE_SIZE];
    }

}

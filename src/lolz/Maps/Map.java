package lolz.Maps;

import lolz.Main;
import lolz.Player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class Map {
    public Player player;
    public int WIDTH, HEIGHT;
    public int VIRTUAL_WIDTH, VIRTUAL_HEIGHT;
    public int AREA, VIRTUAL_AREA;

    public enum Tile {
        EMPTY("empty", false),
        GROUND("ground", false),
        WALL("wall", true),
        WALL_LEFT("wall left", true),
        WALL_RIGHT("wall right", true),
        WALL_TOP("wall top", true);

        public final String name;
        public final boolean solid;

        private Tile(String name, boolean solid) {
            this.name = name;
            this.solid = solid;
        }
    }

    private HashMap<Tile, Image> tilePics;

    public Tile[][] tiles;

    Map(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.VIRTUAL_WIDTH = this.WIDTH / Main.TILE_SIZE;
        this.VIRTUAL_HEIGHT = this.HEIGHT / Main.TILE_SIZE;
        this.AREA = this.WIDTH * this.HEIGHT;
        this.VIRTUAL_AREA = this.VIRTUAL_WIDTH + this.VIRTUAL_HEIGHT;
        this.tilePics = new HashMap<Tile, Image>();
        try {
            tilePics.put(Tile.GROUND, ImageIO.read(new File("res/tiles/floor_1.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
            tilePics.put(Tile.WALL, ImageIO.read(new File("res/tiles/wall_mid.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
            tilePics.put(Tile.WALL_LEFT, ImageIO.read(new File("res/tiles/wall_side_mid_left.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
            tilePics.put(Tile.WALL_RIGHT, ImageIO.read(new File("res/tiles/wall_side_mid_right.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
            tilePics.put(Tile.WALL_TOP, ImageIO.read(new File("res/tiles/wall_top_mid.png")).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

        // set tiles to empty by default
        this.tiles = new Tile[this.VIRTUAL_HEIGHT][this.VIRTUAL_WIDTH];
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                tiles[y][x] = Tile.EMPTY;
            }
        }
    }

    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int) -(this.player.x - Main.WIDTH / 2), (int) -(this.player.y - Main.HEIGHT / 2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (this.tiles[y][x] != Tile.EMPTY) {
                    g.drawImage(this.tilePics.get(this.tiles[y][x]), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                    switch (this.tiles[y][x]) {
                        case WALL:
                            g.drawImage(this.tilePics.get(Tile.WALL_TOP), Main.TILE_SIZE * x, Main.TILE_SIZE * (y - 1), null);
                            break;
                        case WALL_LEFT:
                        case WALL_RIGHT:
                            g.drawImage(this.tilePics.get(this.tiles[y][x]), Main.TILE_SIZE * x, Main.TILE_SIZE * (y - 1), null);
                            break;
                    }
                }
            }
        }

        // draw player
        this.player.paint(g);
    }

    public abstract void update(int time);

    public Tile get_tile_at(int x, int y) {
        if (x < 0 || x >= this.WIDTH || y < 0 || y >= this.HEIGHT) {
            return null;
        }
        return this.tiles[y / Main.TILE_SIZE][x / Main.TILE_SIZE];
    }

    Tile get_tile_at_virtual(int x, int y) {
        if (x < 0 || x >= this.VIRTUAL_WIDTH || y < 0 || y >= this.VIRTUAL_HEIGHT) {
            return null;
        }
        return this.tiles[y][x];
    }

}

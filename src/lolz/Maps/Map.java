package lolz.Maps;

import lolz.Main;
import lolz.Player;

import java.awt.*;

public abstract class Map {
    public Player player;
    int WIDTH, HEIGHT;

    public enum Tile {GROUND, WALL}

    Tile[][] tiles;  // 0 -> ground, 1 -> wall

    public abstract void paint(Graphics g);

    public abstract void update(int time);

    public Tile tile_at(int x, int y) {
        return this.tiles[y / Main.TILE_SIZE][x / Main.TILE_SIZE];
    }

}

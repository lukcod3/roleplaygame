package lolz.Maps;

import lolz.GUI.Tile;
import lolz.Main;
import lolz.Player.Player;
import lolz.Monster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import lolz.GUI.Tile.StaticTile;

public abstract class Map {
    public Player player;
    public Monster monster;
    public int WIDTH, HEIGHT;
    public int VIRTUAL_WIDTH, VIRTUAL_HEIGHT;
    public int AREA, VIRTUAL_AREA;

    public Tile[][] tiles;

    Map(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.VIRTUAL_WIDTH = this.WIDTH / Main.TILE_SIZE;
        this.VIRTUAL_HEIGHT = this.HEIGHT / Main.TILE_SIZE;
        this.AREA = this.WIDTH * this.HEIGHT;
        this.VIRTUAL_AREA = this.VIRTUAL_WIDTH + this.VIRTUAL_HEIGHT;

        // set tiles to empty by default
        this.tiles = new Tile[this.VIRTUAL_HEIGHT][this.VIRTUAL_WIDTH];
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                tiles[y][x] = new Tile();
            }
        }
    }

    private Image load_image(String path) throws IOException {
        return ImageIO.read(new File(path)).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH);
    }

    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int) -(this.player.x - Main.WIDTH / 2), (int) -(this.player.y - Main.HEIGHT / 2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (!this.tiles[y][x].isEmpty()) {
                    g.drawImage(Tile.tilePics.get(this.tiles[y][x].activeTiles.get(0)), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                }
            }
        }

        // draw wall decos
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (this.tiles[y][x].multipleLayouts()) {
                    for (StaticTile t : this.tiles[y][x].activeTiles.subList(1, this.tiles[y][x].activeTiles.size())) {
                        g.drawImage(Tile.tilePics.get(t), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                    }
                }
            }
        }
        // draw monster
        this.monster.paint(g);
        // draw player, needs to be called last
        this.player.paint(g);
    }

    public abstract void update(int time);

    public Tile get_tile_at(int x, int y) {
        if (x < 0 || x >= this.WIDTH || y < 0 || y >= this.HEIGHT) {
            return null;
        }
        return this.tiles[y / Main.TILE_SIZE][x / Main.TILE_SIZE];
    }

    private Tile get_tile_at_virtual(int x, int y) {
        if (x < 0 || x >= this.VIRTUAL_WIDTH || y < 0 || y >= this.VIRTUAL_HEIGHT) {
            return null;
        }
        return this.tiles[y][x];
    }

    private boolean tile_contains(int x, int y, StaticTile ts) {
        Tile t = this.get_tile_at_virtual(x, y);
        if (t == null) {
            return false;
        } else {
            return t.contains(ts);
        }
    }

    private boolean tile_is_wall(int x, int y) {
        Tile t = this.get_tile_at_virtual(x, y);
        if (t == null) {
            return false;
        } else {
            return t.isWall();
        }
    }

    private boolean tile_is_left_wall(int x, int y) {
        Tile t = this.get_tile_at_virtual(x, y);
        if (t == null) {
            return false;
        } else {
            return t.isLeftWall();
        }
    }

    private boolean tile_is_right_wall(int x, int y) {
        Tile t = this.get_tile_at_virtual(x, y);
        if (t == null) {
            return false;
        } else {
            return t.isRightWall();
        }
    }

    void makeWalls() {
        // remove all walls with only wall over or under it
        boolean done = false;
        while (!done) {
            done = true;
            for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
                for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                    if (this.tiles[y][x].isEmpty()) {
                        // check for nearby ground tiles
                        int n = 0;
                        if (this.tile_contains(x, y - 1, StaticTile.GROUND)) {
                            n++;
                        }
                        if (this.tile_contains(x + 1, y, StaticTile.GROUND)) {
                            n++;
                        }
                        if (this.tile_contains(x, y + 1, StaticTile.GROUND)) {
                            n++;
                        }
                        if (this.tile_contains(x - 1, y, StaticTile.GROUND)) {
                            n++;
                        }

                        if (n == 3 || n == 4) {
                            this.tiles[y][x].add(StaticTile.GROUND);
                            done = false;
                        }
                    }
                }
            }
        }


        // add walls
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                if (this.tiles[y][x].isEmpty()) {
                    // check for nearby ground tiles
                    boolean[] ground = new boolean[4]; // 0 -> north, 1 -> east, 2 -> south, 3 -> west
                    if (this.tile_contains(x, y - 1, StaticTile.GROUND)) {
                        ground[0] = true;
                    }
                    if (this.tile_contains(x + 1, y, StaticTile.GROUND)) {
                        ground[1] = true;
                    }
                    if (this.tile_contains(x, y + 1, StaticTile.GROUND)) {
                        ground[2] = true;
                    }
                    if (this.tile_contains(x - 1, y, StaticTile.GROUND)) {
                        ground[3] = true;
                    }

                    // add front/side walls
                    if (ground[0] || ground[2]) {
                        this.tiles[y][x].add(StaticTile.WALL);
                        this.tiles[y - 1][x].add(StaticTile.WALL_TOP_MID);
                    } else if (ground[1]) {
                        this.tiles[y][x].add(StaticTile.WALL_SIDE_MID_LEFT);
                        this.tiles[y - 1][x].add(StaticTile.WALL_SIDE_MID_LEFT);
                    } else if (ground[3]) {
                        this.tiles[y][x].add(StaticTile.WALL_SIDE_MID_RIGHT);
                        this.tiles[y - 1][x].add(StaticTile.WALL_SIDE_MID_RIGHT);
                    }
                }
            }
        }

        // transform edge walls and wall tops
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                // transform edge walls to wall_left or wall_right
                if (this.tile_contains(x, y, StaticTile.WALL)) {
                    if (!this.tile_is_wall(x + 1, y) && !this.tile_is_wall(x - 1, y)) {
                        this.tiles[y][x].remove(StaticTile.WALL);
                        this.tiles[y][x].add(StaticTile.WALL_LEFT_RIGHT);
                    } else if (!this.tile_is_wall(x + 1, y)) {
                        this.tiles[y][x].remove(StaticTile.WALL);
                        this.tiles[y][x].add(StaticTile.WALL_RIGHT);
                        this.tiles[y - 1][x].remove(StaticTile.WALL_TOP_MID);
                        this.tiles[y - 1][x].add(StaticTile.WALL_TOP_RIGHT);
                    } else if (!this.tile_is_wall(x - 1, y)) {
                        this.tiles[y][x].remove(StaticTile.WALL);
                        this.tiles[y][x].add(StaticTile.WALL_LEFT);
                        this.tiles[y - 1][x].remove(StaticTile.WALL_TOP_MID);
                        this.tiles[y - 1][x].add(StaticTile.WALL_TOP_LEFT);
                    }
                }
            }
        }


        // add special walls
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                // kleine punkte um wall_top mit wall_side zu verbinden
                if (this.tile_is_wall(x + 1, y + 1) && this.tile_contains(x, y + 1, StaticTile.WALL_SIDE_MID_LEFT)) {
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_TOP_LEFT);
                }
                if (this.tile_is_wall(x - 1, y + 1) && this.tile_contains(x, y + 1, StaticTile.WALL_SIDE_MID_RIGHT)) {
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_TOP_RIGHT);
                }

                // striche um wall_side mit wall zu verbinden
                if (this.tile_is_left_wall(x + 1, y) && this.tile_contains(x, y - 1, StaticTile.WALL_SIDE_MID_LEFT)) {
                    this.tiles[y][x+1].remove(StaticTile.WALL_LEFT);
                    this.tiles[y][x+1].add(StaticTile.WALL);
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_FRONT_LEFT);
                }
                if (this.tile_is_right_wall(x - 1, y) && this.tile_contains(x, y - 1, StaticTile.WALL_SIDE_MID_RIGHT)) {
                    this.tiles[y][x-1].remove(StaticTile.WALL_RIGHT);
                    this.tiles[y][x-1].add(StaticTile.WALL);
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_FRONT_RIGHT);
                }

                // seitenwand nach unten ziehen
                if (this.tile_is_right_wall(x, y) && (this.tile_is_right_wall(x, y + 1) || this.tile_contains(x, y + 1, StaticTile.WALL_SIDE_MID_LEFT) || this.tile_is_left_wall(x + 1, y + 1))) {
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_MID_LEFT);
                }
                if (this.tile_is_left_wall(x, y) && (this.tile_is_left_wall(x, y + 1) || this.tile_contains(x, y + 1, StaticTile.WALL_SIDE_MID_RIGHT) || this.tile_is_right_wall(x - 1, y + 1))) {
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_MID_RIGHT);
                }
            }
        }

        // final validation
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                tiles[y][x].validate();
            }
        }
    }

}

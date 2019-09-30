package lolz.Maps;

import lolz.Entity.Entity;
import lolz.GUI.Tile;
import lolz.Main;
import lolz.Entity.Player;
import lolz.Entity.Monster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import lolz.GUI.Tile.StaticTile;

public abstract class Map {
    public Player player;
    public Monster monster;
    public int WIDTH, HEIGHT;
    public int VIRTUAL_WIDTH, VIRTUAL_HEIGHT;
    public int AREA, VIRTUAL_AREA;
    ArrayList<Entity> entities;

    public Tile[][] tiles;

    Map(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.VIRTUAL_WIDTH = this.WIDTH / Main.TILE_SIZE;
        this.VIRTUAL_HEIGHT = this.HEIGHT / Main.TILE_SIZE;
        this.AREA = this.WIDTH * this.HEIGHT;
        this.VIRTUAL_AREA = this.VIRTUAL_WIDTH + this.VIRTUAL_HEIGHT;
        this.entities = new ArrayList<>();

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
                for (StaticTile t : this.tiles[y][x].baseTiles) {
                    g.drawImage(Tile.tilePics.get(t), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                }
            }
        }

        this.entities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return Double.compare(o1.y, o2.y);
            }
        });

        int y = 0;
        int entitiy_index = 0;
        while (y < this.tiles.length) {
            while (entitiy_index < this.entities.size() && (int) (this.entities.get(entitiy_index).y + this.player.getHeight()) / Main.TILE_SIZE == y) {
                this.entities.get(entitiy_index).paint(g);
                entitiy_index++;
            }
            for (int x = 0; x < this.tiles[0].length; x++) {
                for (StaticTile t : this.tiles[y][x].topTiles) {
                    g.drawImage(Tile.tilePics.get(t), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                }
            }
            y++;
        }

        // translate back
        g.translate((int) (this.player.x - Main.WIDTH / 2), (int) (this.player.y - Main.HEIGHT / 2));

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
        // possibilities for floor mutation (starting from index 2 (bc 1 is standard))
        StaticTile[] floors = {StaticTile.FLOOR_2, StaticTile.FLOOR_3, StaticTile.FLOOR_4, StaticTile.FLOOR_5, StaticTile.FLOOR_6, StaticTile.FLOOR_7, StaticTile.FLOOR_8};
        double[] poss = {0.05, 0.05, 0.1, 0.1, 0.1, 0.1, 0.1};

        // remove all walls with only wall over or under it
        boolean done = false;
        while (!done) {
            done = true;
            for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
                for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                    if (this.tiles[y][x].isEmpty()) {
                        // check for nearby ground tiles
                        int n = 0;
                        if (this.tile_contains(x, y - 1, StaticTile.FLOOR_1)) {
                            n++;
                        }
                        if (this.tile_contains(x + 1, y, StaticTile.FLOOR_1)) {
                            n++;
                        }
                        if (this.tile_contains(x, y + 1, StaticTile.FLOOR_1)) {
                            n++;
                        }
                        if (this.tile_contains(x - 1, y, StaticTile.FLOOR_1)) {
                            n++;
                        }

                        if (n == 3 || n == 4) {
                            this.tiles[y][x].add(StaticTile.FLOOR_1);
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
                    if (this.tile_contains(x, y - 1, StaticTile.FLOOR_1)) {
                        ground[0] = true;
                    }
                    if (this.tile_contains(x + 1, y, StaticTile.FLOOR_1)) {
                        ground[1] = true;
                    }
                    if (this.tile_contains(x, y + 1, StaticTile.FLOOR_1)) {
                        ground[2] = true;
                    }
                    if (this.tile_contains(x - 1, y, StaticTile.FLOOR_1)) {
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
                    this.tiles[y][x + 1].remove(StaticTile.WALL_LEFT);
                    this.tiles[y][x + 1].add(StaticTile.WALL);
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_FRONT_LEFT);
                }
                if (this.tile_is_right_wall(x - 1, y) && this.tile_contains(x, y - 1, StaticTile.WALL_SIDE_MID_RIGHT)) {
                    this.tiles[y][x - 1].remove(StaticTile.WALL_RIGHT);
                    this.tiles[y][x - 1].add(StaticTile.WALL);
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_FRONT_RIGHT);
                }

                // seitenwand nach unten ziehen
                if (this.tile_is_right_wall(x, y) && (this.tile_is_right_wall(x, y + 1) || this.tile_contains(x, y + 1, StaticTile.WALL_SIDE_MID_LEFT) || this.tile_is_left_wall(x + 1, y + 1))) {
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_MID_LEFT);
                    this.tiles[y - 1][x].remove(StaticTile.WALL_TOP_RIGHT);
                    this.tiles[y - 1][x].add(StaticTile.WALL_CORNER_TOP_RIGHT);
                }
                if (this.tile_is_left_wall(x, y) && (this.tile_is_left_wall(x, y + 1) || this.tile_contains(x, y + 1, StaticTile.WALL_SIDE_MID_RIGHT) || this.tile_is_right_wall(x - 1, y + 1))) {
                    this.tiles[y][x].add(StaticTile.WALL_SIDE_MID_RIGHT);
                    this.tiles[y - 1][x].remove(StaticTile.WALL_TOP_LEFT);
                    this.tiles[y - 1][x].add(StaticTile.WALL_CORNER_TOP_LEFT);
                }
            }
        }

        // mutate floor
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                if (this.tile_contains(x, y, StaticTile.FLOOR_1)) {
                    if (this.tiles[y][x - 1].contains(StaticTile.WALL_SIDE_MID_LEFT)) {
                        if (Math.random() < poss[2]) {
                            this.tiles[y][x].remove(StaticTile.FLOOR_1);
                            this.tiles[y][x].add(floors[2]);
                        } else if (Math.random() < poss[3]) {
                            this.tiles[y][x].remove(StaticTile.FLOOR_1);
                            this.tiles[y][x].add(floors[3]);
                        }
                    } else if (this.tiles[y][x + 1].contains(StaticTile.WALL_SIDE_MID_RIGHT)) {
                        if (Math.random() < poss[4]) {
                            this.tiles[y][x].remove(StaticTile.FLOOR_1);
                            this.tiles[y][x].add(floors[4]);
                        } else if (Math.random() < poss[5]) {
                            this.tiles[y][x].remove(StaticTile.FLOOR_1);
                            this.tiles[y][x].add(floors[5]);
                        }
                    } else {
                        if (Math.random() < poss[0]) {
                            this.tiles[y][x].remove(StaticTile.FLOOR_1);
                            this.tiles[y][x].add(floors[0]);
                        } else if (Math.random() < poss[1]) {
                            this.tiles[y][x].remove(StaticTile.FLOOR_1);
                            this.tiles[y][x].add(floors[1]);
                        }
                    }
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

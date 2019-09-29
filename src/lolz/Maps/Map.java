package lolz.Maps;

import lolz.Main;
import lolz.Player.Player;
import lolz.Monster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public abstract class Map {
    public Player player;
    public Monster monster;
    public int WIDTH, HEIGHT;
    public int VIRTUAL_WIDTH, VIRTUAL_HEIGHT;
    public int AREA, VIRTUAL_AREA;

    private HashMap<Tile, Image> tilePics;
    public Tile[][] tiles;

    public enum Tile {
        EMPTY("empty", true),
        GROUND("ground", false),
        WALL("wall", true),
        WALL_LEFT("wall left", true),
        WALL_RIGHT("wall right", true),
        WALL_SIDE_MID_LEFT("wall side mid left", true),
        WALL_SIDE_MID_RIGHT("wall side mid right", true),
        WALL_TOP("wall top", true),
        WALL_SIDE_LEFT_RIGHT("side left right", true, new Tile[]{WALL_SIDE_MID_LEFT, WALL_SIDE_MID_RIGHT}),
        WALL_SIDE_TOP_LEFT("wall side top left", true),
        WALL_SIDE_TOP_RIGHT("wall side top right", true),
        WALL_INNER_CORNER_MID_RIGHT("wall inner corner mid right", true),
        WALL_INNER_CORNER_MID_LEFT("wall inner corner mid left", true),
        WALL_INNER_CORNER_L_TOP_LEFT("wall inner corner l top left", true),
        WALL_INNER_CORNER_L_TOP_RIGHT("wall inner corner l top right", true),
        WALL_CORNER_BOTTOM_LEFT("wall corner bottom left", true),
        WALL_CORNER_BOTTOM_RIGHT("wall corner bottom right", true),
        GROUND_WALL_TOP("ground with wall top", false, new Tile[]{GROUND, WALL_TOP}),
        BOTTOM_WALL_TOP("bottom wall top", true, new Tile[]{WALL, WALL_TOP}),
        WALL_OUTER_TOP_LEFT("wall outer top left", true, new Tile[]{WALL, WALL_SIDE_MID_RIGHT}),
        WALL_OUTER_TOP_RIGHT("wall outer top right", true, new Tile[]{WALL, WALL_SIDE_MID_LEFT}),
        WALL_OUTER_TOP_LEFT_WITH_WALL_TOP("wall outer top left with wall top", true, new Tile[]{WALL, WALL_SIDE_MID_RIGHT, WALL_TOP}),
        WALL_OUTER_TOP_RIGHT_WITH_WALL_TOP("wall outer top left with wall top", true, new Tile[]{WALL, WALL_SIDE_MID_RIGHT, WALL_TOP}),
        WALL_SIDE_FRONT_LEFT("wall side front left", true),
        WALL_SIDE_FRONT_RIGHT("wall side front right", true);

        public final String name;
        public final boolean solid;
        public final boolean multipleLayouts;
        public final Tile[] layouts;

        Tile(String name, boolean solid) {
            this.name = name;
            this.solid = solid;
            this.multipleLayouts = false;
            this.layouts = new Tile[0];
        }

        Tile(String name, boolean solid, Tile[] layouts) {
            this.name = name;
            this.solid = solid;
            this.multipleLayouts = true;
            this.layouts = layouts;
        }

    }

    Map(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.VIRTUAL_WIDTH = this.WIDTH / Main.TILE_SIZE;
        this.VIRTUAL_HEIGHT = this.HEIGHT / Main.TILE_SIZE;
        this.AREA = this.WIDTH * this.HEIGHT;
        this.VIRTUAL_AREA = this.VIRTUAL_WIDTH + this.VIRTUAL_HEIGHT;
        this.tilePics = new HashMap<>();
        try {
            tilePics.put(Tile.GROUND, load_image("res/tiles/floor_1.png"));
            tilePics.put(Tile.WALL, load_image("res/tiles/wall_mid.png"));
            tilePics.put(Tile.WALL_LEFT, load_image("res/tiles/wall_left.png"));
            tilePics.put(Tile.WALL_RIGHT, load_image("res/tiles/wall_right.png"));
            tilePics.put(Tile.WALL_SIDE_MID_LEFT, load_image("res/tiles/wall_side_mid_left.png"));
            tilePics.put(Tile.WALL_SIDE_MID_RIGHT, load_image("res/tiles/wall_side_mid_right.png"));
            tilePics.put(Tile.WALL_TOP, load_image("res/tiles/wall_top_mid.png"));
            tilePics.put(Tile.WALL_SIDE_TOP_LEFT, load_image("res/tiles/wall_side_top_left.png"));
            tilePics.put(Tile.WALL_SIDE_TOP_RIGHT, load_image("res/tiles/wall_side_top_right.png"));
            tilePics.put(Tile.WALL_INNER_CORNER_MID_RIGHT, load_image("res/tiles/wall_inner_corner_mid_right.png"));
            tilePics.put(Tile.WALL_INNER_CORNER_MID_LEFT, load_image("res/tiles/wall_inner_corner_mid_left.png"));
            tilePics.put(Tile.WALL_INNER_CORNER_L_TOP_LEFT, load_image("res/tiles/wall_inner_corner_l_top_left.png"));
            tilePics.put(Tile.WALL_INNER_CORNER_L_TOP_RIGHT, load_image("res/tiles/wall_inner_corner_l_top_right.png"));
            tilePics.put(Tile.WALL_CORNER_BOTTOM_LEFT, load_image("res/tiles/wall_corner_bottom_left.png"));
            tilePics.put(Tile.WALL_CORNER_BOTTOM_RIGHT, load_image("res/tiles/wall_corner_bottom_right.png"));
            tilePics.put(Tile.WALL_SIDE_FRONT_LEFT, load_image("res/tiles/wall_side_front_left.png"));
            tilePics.put(Tile.WALL_SIDE_FRONT_RIGHT, load_image("res/tiles/wall_side_front_right.png"));
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

    private Image load_image(String path) throws IOException {
        return ImageIO.read(new File(path)).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH);
    }

    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int) -(this.player.x - Main.WIDTH / 2), (int) -(this.player.y - Main.HEIGHT / 2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (this.tiles[y][x] != Tile.EMPTY && !this.tiles[y][x].multipleLayouts) {
                    g.drawImage(this.tilePics.get(this.tiles[y][x]), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                } else if (this.tiles[y][x].multipleLayouts) {
                    g.drawImage(this.tilePics.get(this.tiles[y][x].layouts[0]), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                }
            }
        }

        // draw wall decos
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (this.tiles[y][x] != Tile.EMPTY) {
                    if (this.tiles[y][x].multipleLayouts) {
                        for (Tile t : Arrays.copyOfRange(this.tiles[y][x].layouts, 1, this.tiles[y][x].layouts.length)) {
                            g.drawImage(this.tilePics.get(t), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                        }
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

    void makeWalls() {
        // remove all walls with only wall over or under it
        boolean done = false;
        while (!done) {
            done = true;
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

                        if (n == 3 || n == 4) {
                            this.tiles[y][x] = Tile.GROUND;
                            done = false;
                        }
                    }
                }
            }
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

                    switch (n) {
                        case 1:
                            if (ground[1]) {
                                this.tiles[y][x] = Tile.WALL_SIDE_MID_LEFT;
                                if (this.get_tile_at_virtual(x, y - 1) == Tile.EMPTY) {
                                    this.tiles[y - 1][x] = Tile.WALL_SIDE_MID_LEFT;
                                } else if (this.get_tile_at_virtual(x, y - 1) == Tile.WALL) {
                                    this.tiles[y - 1][x] = Tile.WALL_OUTER_TOP_RIGHT;
                                }
                            } else if (ground[3]) {
                                this.tiles[y][x] = Tile.WALL_SIDE_MID_RIGHT;
                                if (this.get_tile_at_virtual(x, y - 1) == Tile.EMPTY) {
                                    this.tiles[y - 1][x] = Tile.WALL_SIDE_MID_RIGHT;
                                } else {
                                    //this.tiles[y - 1][x] = Tile.WALL_OUTER_TOP_LEFT;
                                }
                            } else if (ground[0]) {
                                this.tiles[y][x] = Tile.WALL;
                                this.tiles[y - 1][x] = Tile.GROUND_WALL_TOP;
                            } else {
                                this.tiles[y][x] = Tile.WALL;
                                switch (this.tiles[y - 1][x]) {
                                    case EMPTY:
                                        this.tiles[y - 1][x] = Tile.WALL_TOP;
                                        break;
                                    case WALL:
                                    case WALL_LEFT:
                                    case WALL_RIGHT:
                                        this.tiles[y - 1][x] = Tile.BOTTOM_WALL_TOP;
                                        break;
                                }
                            }
                            break;

                        case 2:
                            if (ground[0]) {
                                if (ground[1]) {
                                    this.tiles[y][x] = Tile.WALL_OUTER_TOP_RIGHT;
                                    this.tiles[y - 1][x] = Tile.GROUND_WALL_TOP;
                                } else if (ground[2]) {
                                    break;
                                } else {
                                    this.tiles[y][x] = Tile.WALL_OUTER_TOP_LEFT;
                                    this.tiles[y - 1][x] = Tile.GROUND_WALL_TOP;
                                }
                            } else if (ground[1]) {
                                if (ground[2]) {
                                    this.tiles[y][x] = Tile.WALL_RIGHT;
                                } else if (ground[3]) {
                                    this.tiles[y][x] = Tile.WALL_SIDE_LEFT_RIGHT;
                                }
                            } else if (ground[2]) {
                                this.tiles[y][x] = Tile.WALL_LEFT;
                            }
                            break;
                    }
                }
            }
        }


        // add special walls
        for (int y = 0; y < this.VIRTUAL_HEIGHT; y++) {
            for (int x = 0; x < this.VIRTUAL_WIDTH; x++) {
                switch (this.tiles[y][x]) {
                    case EMPTY:
                        // check for nearby ground tiles
                        // kleine punkte um wall_top mit wall_side zu verbinden
                        if (this.get_tile_at_virtual(x + 1, y + 1) == Tile.WALL && this.get_tile_at_virtual(x, y + 1) == Tile.WALL_SIDE_MID_LEFT) {
                            this.tiles[y][x] = Tile.WALL_SIDE_TOP_LEFT;
                        } else if (this.get_tile_at_virtual(x - 1, y + 1) == Tile.WALL && this.get_tile_at_virtual(x, y + 1) == Tile.WALL_SIDE_MID_RIGHT) {
                            this.tiles[y][x] = Tile.WALL_SIDE_TOP_RIGHT;
                            // striche um wall_side mit wall zu verbinden
                        } else if (this.get_tile_at_virtual(x + 1, y) == Tile.WALL && this.get_tile_at_virtual(x, y - 1) == Tile.WALL_SIDE_MID_LEFT) {
                            this.tiles[y][x] = Tile.WALL_SIDE_FRONT_LEFT;
                        } else if (this.get_tile_at_virtual(x - 1, y) == Tile.WALL && this.get_tile_at_virtual(x, y - 1) == Tile.WALL_SIDE_MID_RIGHT) {
                            this.tiles[y][x] = Tile.WALL_SIDE_FRONT_RIGHT;
                        }
                        break;

                    case WALL_OUTER_TOP_LEFT:
                        // falls eine art halbinsel entsteht (zwei wände übereinander, dann nach links),
                        // soll auch die top der unteren wand angezeigt werden
                        if (this.get_tile_at_virtual(x, y + 1) == Tile.WALL_LEFT) {
                            this.tiles[y][x] = Tile.WALL_OUTER_TOP_LEFT_WITH_WALL_TOP;
                        }
                        break;

                    case WALL_OUTER_TOP_RIGHT:
                        if (this.get_tile_at_virtual(x, y + 1) == Tile.WALL_RIGHT) {
                            this.tiles[y][x] = Tile.WALL_OUTER_TOP_RIGHT_WITH_WALL_TOP;
                        }
                        break;

                    case WALL_SIDE_MID_LEFT:
                        // aeussere ecken fixen
                        if (this.get_tile_at_virtual(x, y + 1) == Tile.WALL_RIGHT) {
                            this.tiles[y][x] = Tile.WALL_CORNER_BOTTOM_RIGHT;
                        }
                        break;

                    case WALL_SIDE_MID_RIGHT:
                        // aeussere ecken fixen
                        if (this.get_tile_at_virtual(x, y + 1) == Tile.WALL_LEFT) {
                            this.tiles[y][x] = Tile.WALL_CORNER_BOTTOM_LEFT;
                        }
                        break;
                }
            }
        }
    }

}

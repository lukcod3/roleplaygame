package lolz.GUI;

import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tile {
    public enum StaticTile {
        EMPTY("empty", true, true),
        GROUND("ground", false,true),
        WALL("wall", true,true),
        WALL_LEFT("wall left", true,true),
        WALL_RIGHT("wall right", true,true),
        WALL_LEFT_RIGHT("wall left right", true, true),
        WALL_SIDE_MID_LEFT("wall side mid left", true, false),
        WALL_SIDE_MID_RIGHT("wall side mid right", true, false),
        WALL_TOP_MID("wall top mid", false, false),
        WALL_TOP_LEFT("wall top left", false, false),
        WALL_TOP_RIGHT("wall top right", false, false),
        WALL_SIDE_TOP_LEFT("wall side top left", false, false),
        WALL_SIDE_TOP_RIGHT("wall side top right", false, false),
        WALL_INNER_CORNER_MID_RIGHT("wall inner corner mid right", true, false),
        WALL_INNER_CORNER_MID_LEFT("wall inner corner mid left", true, false),
        WALL_CORNER_TOP_LEFT("wall corner top left", false, false),
        WALL_CORNER_TOP_RIGHT("wall corner top right", false, false),
        WALL_INNER_CORNER_T_TOP_LEFT("wall inner corner t top left", false, false),
        WALL_INNER_CORNER_T_TOP_RIGHT("wall inner corner t top right", false, false),
        WALL_CORNER_BOTTOM_LEFT("wall corner bottom left", true, false),
        WALL_CORNER_BOTTOM_RIGHT("wall corner bottom right", true, false),
        WALL_SIDE_FRONT_LEFT("wall side front left", false,true),
        WALL_SIDE_FRONT_RIGHT("wall side front right", false,true);

        public final String name;
        public final boolean solid;
        public final boolean isBase;

        StaticTile(String name, boolean solid, boolean isBase) {
            this.name = name;
            this.solid = solid;
            this.isBase = isBase;
        }

    }

    public static HashMap<StaticTile, Image> tilePics;
    private static HashMap<StaticTile[], StaticTile> fusionMap;
    public ArrayList<StaticTile> baseTiles;
    public ArrayList<StaticTile> topTiles;


    static {
        tilePics = new HashMap<>();
        try {
            tilePics.put(StaticTile.GROUND, load_image("res/tiles/floor_1.png"));
            tilePics.put(StaticTile.WALL, load_image("res/tiles/wall_mid.png"));
            tilePics.put(StaticTile.WALL_LEFT, load_image("res/tiles/wall_left.png"));
            tilePics.put(StaticTile.WALL_RIGHT, load_image("res/tiles/wall_right.png"));
            tilePics.put(StaticTile.WALL_LEFT_RIGHT, load_image("res/tiles/wall_left_right.png"));
            tilePics.put(StaticTile.WALL_SIDE_MID_LEFT, load_image("res/tiles/wall_side_mid_left.png"));
            tilePics.put(StaticTile.WALL_SIDE_MID_RIGHT, load_image("res/tiles/wall_side_mid_right.png"));
            tilePics.put(StaticTile.WALL_TOP_MID, load_image("res/tiles/wall_top_mid.png"));
            tilePics.put(StaticTile.WALL_TOP_LEFT, load_image("res/tiles/wall_top_left.png"));
            tilePics.put(StaticTile.WALL_TOP_RIGHT, load_image("res/tiles/wall_top_right.png"));
            tilePics.put(StaticTile.WALL_SIDE_TOP_LEFT, load_image("res/tiles/wall_side_top_left.png"));
            tilePics.put(StaticTile.WALL_SIDE_TOP_RIGHT, load_image("res/tiles/wall_side_top_right.png"));
            tilePics.put(StaticTile.WALL_INNER_CORNER_MID_RIGHT, load_image("res/tiles/wall_inner_corner_mid_right.png"));
            tilePics.put(StaticTile.WALL_INNER_CORNER_MID_LEFT, load_image("res/tiles/wall_inner_corner_mid_left.png"));
            tilePics.put(StaticTile.WALL_CORNER_TOP_LEFT, load_image("res/tiles/wall_corner_top_left.png"));
            tilePics.put(StaticTile.WALL_CORNER_TOP_RIGHT, load_image("res/tiles/wall_corner_top_right.png"));
            tilePics.put(StaticTile.WALL_INNER_CORNER_T_TOP_LEFT, load_image("res/tiles/wall_inner_corner_t_top_left.png"));
            tilePics.put(StaticTile.WALL_INNER_CORNER_T_TOP_RIGHT, load_image("res/tiles/wall_inner_corner_t_top_right.png"));
            tilePics.put(StaticTile.WALL_CORNER_BOTTOM_LEFT, load_image("res/tiles/wall_corner_bottom_left.png"));
            tilePics.put(StaticTile.WALL_CORNER_BOTTOM_RIGHT, load_image("res/tiles/wall_corner_bottom_right.png"));
            tilePics.put(StaticTile.WALL_SIDE_FRONT_LEFT, load_image("res/tiles/wall_side_front_left.png"));
            tilePics.put(StaticTile.WALL_SIDE_FRONT_RIGHT, load_image("res/tiles/wall_side_front_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        fusionMap = new HashMap<>();
        fusionMap.put(new StaticTile[]{StaticTile.WALL_SIDE_MID_LEFT, StaticTile.WALL_TOP_RIGHT}, StaticTile.WALL_CORNER_BOTTOM_RIGHT);
        fusionMap.put(new StaticTile[]{StaticTile.WALL_SIDE_MID_RIGHT, StaticTile.WALL_TOP_LEFT}, StaticTile.WALL_CORNER_BOTTOM_LEFT);
    }

    public Tile() {
        this.baseTiles = new ArrayList<>();
        this.topTiles = new ArrayList<>();
    }

    private static Image load_image(String path) throws IOException {
        return ImageIO.read(new File(path)).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH);
    }

    public boolean isEmpty() {
        return this.baseTiles.isEmpty() && this.topTiles.isEmpty();
    }

    public boolean contains(StaticTile t) {
        return this.baseTiles.contains(t) || this.topTiles.contains((t));
    }

    public void add(StaticTile t) {
        if (!this.contains(t)) {
            if (t.isBase){
                this.baseTiles.add(t);
            } else {
                this.topTiles.add(t);
            }
        }
    }

    private void add(int index, StaticTile t) {
        if (!this.contains(t)) {
            if (t.isBase){
                this.baseTiles.add(index,t);
            } else {
                this.topTiles.add(index, t);
            }
        }
    }

    public void remove(StaticTile t) {
        if (this.baseTiles.contains(t)) {
            this.baseTiles.remove(t);
        } else if (this.topTiles.contains(t)) {
            this.topTiles.remove(t);
        } else {
            System.out.println("!!!! WARNING: REMOVING STATIC_TILE " + t + " BUT DOESNT EXIST !!!!");
        }
    }

    public boolean isSolid() {
        for (StaticTile t : this.baseTiles) {
            if (t.solid) {
                return true;
            }
        }
        for (StaticTile t : this.topTiles) {
            if (t.solid) {
                return true;
            }
        }
        return false;
    }

    public boolean isWall() {
        return this.contains(StaticTile.WALL_LEFT) || this.contains(StaticTile.WALL) || this.contains(StaticTile.WALL_RIGHT) || this.contains(StaticTile.WALL_LEFT_RIGHT);
    }

    public boolean isLeftWall() {
        return this.contains(StaticTile.WALL_LEFT) || this.contains(StaticTile.WALL_LEFT_RIGHT);
    }

    public boolean isRightWall() {
        return this.contains(StaticTile.WALL_RIGHT) || this.contains(StaticTile.WALL_LEFT_RIGHT);
    }

    public boolean isGround() {
        return this.contains(StaticTile.GROUND);
    }

    public void validate() {
        for (Map.Entry<StaticTile[], StaticTile> entry : fusionMap.entrySet()) {
            if (topTiles.containsAll(Arrays.asList(entry.getKey()))) {
                this.topTiles.removeAll(Arrays.asList(entry.getKey()));
                this.topTiles.add(entry.getValue());
            }
        }
    }

}

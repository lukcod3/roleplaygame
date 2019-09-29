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
        EMPTY("empty", true),
        GROUND("ground", false),
        WALL("wall", true),
        WALL_LEFT("wall left", true),
        WALL_RIGHT("wall right", true),
        WALL_LEFT_RIGHT("wall left right", true),
        WALL_SIDE_MID_LEFT("wall side mid left", true),
        WALL_SIDE_MID_RIGHT("wall side mid right", true),
        WALL_TOP_MID("wall top mid", false),
        WALL_TOP_LEFT("wall top left", false),
        WALL_TOP_RIGHT("wall top right", false),
        WALL_SIDE_TOP_LEFT("wall side top left", false),
        WALL_SIDE_TOP_RIGHT("wall side top right", false),
        WALL_INNER_CORNER_MID_RIGHT("wall inner corner mid right", true),
        WALL_INNER_CORNER_MID_LEFT("wall inner corner mid left", true),
        WALL_INNER_CORNER_L_TOP_LEFT("wall inner corner l top left", true),
        WALL_INNER_CORNER_L_TOP_RIGHT("wall inner corner l top right", true),
        WALL_CORNER_BOTTOM_LEFT("wall corner bottom left", true),
        WALL_CORNER_BOTTOM_RIGHT("wall corner bottom right", true),
        WALL_SIDE_FRONT_LEFT("wall side front left", false),
        WALL_SIDE_FRONT_RIGHT("wall side front right", false);

        public final String name;
        public final boolean solid;

        StaticTile(String name, boolean solid) {
            this.name = name;
            this.solid = solid;
        }

    }

    public static HashMap<StaticTile, Image> tilePics;

    public ArrayList<StaticTile> activeTiles;

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
            tilePics.put(StaticTile.WALL_INNER_CORNER_L_TOP_LEFT, load_image("res/tiles/wall_inner_corner_l_top_left.png"));
            tilePics.put(StaticTile.WALL_INNER_CORNER_L_TOP_RIGHT, load_image("res/tiles/wall_inner_corner_l_top_right.png"));
            tilePics.put(StaticTile.WALL_CORNER_BOTTOM_LEFT, load_image("res/tiles/wall_corner_bottom_left.png"));
            tilePics.put(StaticTile.WALL_CORNER_BOTTOM_RIGHT, load_image("res/tiles/wall_corner_bottom_right.png"));
            tilePics.put(StaticTile.WALL_SIDE_FRONT_LEFT, load_image("res/tiles/wall_side_front_left.png"));
            tilePics.put(StaticTile.WALL_SIDE_FRONT_RIGHT, load_image("res/tiles/wall_side_front_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tile() {
        this.activeTiles = new ArrayList<>();
    }

    private static Image load_image(String path) throws IOException {
        return ImageIO.read(new File(path)).getScaledInstance(Main.TILE_SIZE, Main.TILE_SIZE, Image.SCALE_SMOOTH);
    }

    public boolean isEmpty() {
        return this.activeTiles.isEmpty();
    }

    public boolean multipleLayouts() {
        return this.activeTiles.size() > 1;
    }

    public boolean contains(StaticTile t) {
        return this.activeTiles.contains(t);
    }

    public void add(StaticTile t) {
        if (!this.contains(t)) {
            this.activeTiles.add(t);
        }
    }

    public void add(int index, StaticTile t) {
        if (!this.contains(t)) {
            this.activeTiles.add(index, t);
        }
    }

    public void remove(StaticTile t) {
        if (this.contains(t)) {
            this.activeTiles.remove(t);
        } else {
            System.out.println("!!!! WARNING: REMOVING STATIC_TILE " + t + " BUT DOESNT EXIST !!!!");
        }
    }

    public boolean isSolid() {
        for (StaticTile t : this.activeTiles) {
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

    private void sort() {
        StaticTile[] first = new StaticTile[]{StaticTile.GROUND, StaticTile.WALL, StaticTile.WALL_LEFT, StaticTile.WALL_RIGHT, StaticTile.WALL_LEFT_RIGHT, StaticTile.WALL_SIDE_FRONT_LEFT, StaticTile.WALL_SIDE_FRONT_RIGHT};
        for (StaticTile t : first) {
            if (this.contains(t)) {
                this.remove(t);
                this.add(0, t);
            }
        }
    }

    public void validate() {
        // sort things
        this.sort();

        HashMap<StaticTile[], StaticTile> fusionMap = new HashMap<>();

        // next 2 lines for testing (shouldnt occur later on)
        fusionMap.put(new StaticTile[]{StaticTile.WALL_SIDE_MID_LEFT, StaticTile.WALL_TOP_MID}, StaticTile.WALL_CORNER_BOTTOM_RIGHT);
        fusionMap.put(new StaticTile[]{StaticTile.WALL_SIDE_MID_RIGHT, StaticTile.WALL_TOP_MID}, StaticTile.WALL_CORNER_BOTTOM_LEFT);

        fusionMap.put(new StaticTile[]{StaticTile.WALL_SIDE_MID_LEFT, StaticTile.WALL_TOP_RIGHT}, StaticTile.WALL_CORNER_BOTTOM_RIGHT);
        fusionMap.put(new StaticTile[]{StaticTile.WALL_SIDE_MID_RIGHT, StaticTile.WALL_TOP_LEFT}, StaticTile.WALL_CORNER_BOTTOM_LEFT);

        for (Map.Entry<StaticTile[], StaticTile> entry : fusionMap.entrySet()) {
            if (activeTiles.containsAll(Arrays.asList(entry.getKey()))) {
                this.activeTiles.removeAll(Arrays.asList(entry.getKey()));
                this.activeTiles.add(entry.getValue());
            }
        }
    }

}

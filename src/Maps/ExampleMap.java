package Maps;

import java.awt.*;
public class ExampleMap extends Map {
    private int[][] tiles;  // 0 -> ground, 1 -> wall
    private static final int HEIGHT = 540;
    private final int WIDTH = 960;
    private static final int TILE_SIZE = 50;

    private static final int GROUND = 0;
    private static final int WALL = 1;

    public ExampleMap() {
        this.width = 1200;
        this.height = 1000;

        // setup map
        tiles = new int[this.height / TILE_SIZE][this.width / TILE_SIZE];
        for (int y = 0; y < height / TILE_SIZE && y * TILE_SIZE < HEIGHT; y++) {
            for (int x = 0; x < width / TILE_SIZE && x * TILE_SIZE < WIDTH; x++) {
                tiles[y][x] = GROUND;
            }
        }

        for (int i = 0; i < this.height / TILE_SIZE; i++) {
            tiles[i][0] = WALL;
        }
        for (int i = 0; i < this.height / TILE_SIZE; i++) {
            tiles[this.height / TILE_SIZE - 1][0] = WALL;
        }
        for (int i = 0; i < this.width / TILE_SIZE; i++) {
            tiles[0][i] = WALL;
        }
        for (int i = 0; i < this.width / TILE_SIZE; i++) {
            tiles[0][this.width / TILE_SIZE - 1] = WALL;
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int y = 0; y < height / TILE_SIZE && y * TILE_SIZE < HEIGHT; y++) {
            for (int x = 0; x < width / TILE_SIZE && x * TILE_SIZE < WIDTH; x++) {
                switch (tiles[y][x]) {
                    case GROUND:
                        g.setColor(Color.ORANGE);
                        break;
                    case WALL:
                        g.setColor(Color.GRAY);
                        break;
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    @Override
    public void update(int time) {

    }
}

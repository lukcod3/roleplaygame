package lolz.GUI;

import lolz.Maps.Map;

public class Walker {
    private Map map;
    public int x, y;
    private int dir; // 0 -> north, 1 -> east, 2 -> south, 3 -> west

    public Walker(Map map, int x, int y) {
        // set attributes
        this.map = map;
        this.x = x;
        this.y = y;

        // set direction
        changeDirection();

        // make spawning tile ground
        this.map.tiles[this.y][this.x] = Map.Tile.GROUND;
    }

    public void changeDirection() {
        this.dir = (int) (Math.random() * 4);
    }

    public void move() {
        switch (this.dir) {
            case 0:
                this.y -= 1;
                break;
            case 1:
                this.x += 1;
                break;
            case 2:
                this.y += 1;
                break;
            case 3:
                this.x -= 1;
                break;
        }

        if (this.x < 0) {
            this.x += 1;
        } else if (this.x >= map.VIRTUAL_WIDTH) {
            this.x -= 1;
        }
        if (this.y < 0) {
            this.y += 1;
        } else if (this.y >= map.VIRTUAL_HEIGHT) {
            this.y -= 1;
        }

        // set tile to ground
        if (map.tiles[this.y][this.x] != Map.Tile.GROUND) {
            map.tiles[this.y][this.x] = Map.Tile.GROUND;
        }

    }
}

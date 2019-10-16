package lolz.Maps;

import lolz.Entity.Mage;
import lolz.Entity.Player;
import lolz.GUI.Tile;
import lolz.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Hub extends Map {

    private BufferedImage[][] portal;
    public double portalState;
    public int portalStage;

    public Hub(Player player) {
        super(600, 500);
        this.portalState = 0;
        this.portalStage = 0;
        // spawn player
        //this.player = new Mage(this, this.WIDTH / 2, this.HEIGHT / 2);
        this.player = player;
        this.player.map = this;
        this.player.x = this.WIDTH / 2.0;
        this.player.y = this.HEIGHT / 2.0;
        this.player.directions = new boolean[4];

        // setup map
        for (Tile[] tile : this.tiles) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                tile[x].add(Tile.StaticTile.FLOOR_1);
            }
        }

        // setting walls
        for (Tile[] tile : tiles) {
            tile[0].remove(Tile.StaticTile.FLOOR_1);
            tile[0].add(Tile.StaticTile.WALL);
        }
        for (Tile[] tile : this.tiles) {
            tile[this.tiles[0].length - 1].remove(Tile.StaticTile.FLOOR_1);
            tile[this.tiles[0].length - 1].add(Tile.StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            this.tiles[0][i].remove(Tile.StaticTile.FLOOR_1);
            this.tiles[0][i].add(Tile.StaticTile.WALL);
        }
        for (int i = 0; i < this.tiles[0].length; i++) {
            this.tiles[this.tiles.length - 1][i].remove(Tile.StaticTile.FLOOR_1);
            this.tiles[this.tiles.length - 1][i].add(Tile.StaticTile.WALL);
        }
        portal = new BufferedImage[2][];
        portal[0] = new BufferedImage[8];
        portal[1] = new BufferedImage[6];
        try {
            for(int i = 0; i < 8; i++) {
                portal[0][i] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(i*64, 0, 64, 64);
            }
            for(int i = 0; i < 6; i++) {
                portal[1][i] = ImageIO.read(new File("res/hub/Green Portal Sprite Sheet.png")).getSubimage(i*64, 128, 64, 64);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        // translate map to player coords
        g.translate((int) -(this.player.x - Main.WIDTH / 2), (int) -(this.player.y - Main.HEIGHT / 2));

        // draw map
        for (int y = 0; y < this.tiles.length; y++) {
            for (int x = 0; x < this.tiles[0].length; x++) {
                if (this.tiles[y][x].contains(Tile.StaticTile.FLOOR_1)) {
                    g.drawImage(Tile.tilePics.get(Tile.StaticTile.FLOOR_1), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                } else if (this.tiles[y][x].contains(Tile.StaticTile.WALL)) {
                    g.drawImage(Tile.tilePics.get(Tile.StaticTile.WALL), Main.TILE_SIZE * x, Main.TILE_SIZE * y, null);
                }
            }
        }
        g.drawImage(this.portal[this.portalStage][((int) this.portalState)%(this.portal[this.portalStage].length)].getScaledInstance(120, -1, Image.SCALE_DEFAULT), 430, 40, null);

        // draw player
        this.player.paint(g);

        // translate back
        g.translate((int) (this.player.x - Main.WIDTH / 2), (int) (this.player.y - Main.HEIGHT / 2));
    }


    @Override
    public void update(int time) {
        this.portalState+= 0.1;
        this.player.update(time);
    }
}

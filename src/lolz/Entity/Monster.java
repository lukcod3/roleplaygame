package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Monster extends Entity {
    private int exp, monsterNumber, movementTime;
    public boolean isFollowing;
    public ArrayList<int[]> path;

    public Monster(Map map, int x, int y, int maxHealth, int damage, int armor, int exp, int monsterNumber) {
        super(map, x, y, maxHealth, damage, armor, 0.1);
        this.exp = exp;
        this.monsterNumber = monsterNumber;
        this.path = new ArrayList<>();
        this.isFollowing = false;

        loadImages(this.monsterNumber);

        // this.width = img[0][0].getWidth(null);
        this.width = Main.VIRTUAL_ENTITY_WIDTH;
        this.height = img[0][0].getHeight(null);

        // update before being drawn
        this.update(1);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void update(int time) {
        double CHANGE_DIR_POSS = 0.75;

        movementTime += time;

        if (isFollowing) {
            //System.out.println(Arrays.toString(this.directions));
            followPath(time);
        } else {

            if (movementTime > 1000) {
                movementTime = 0;
                if (Math.random() < CHANGE_DIR_POSS) {
                    int dir = (int) (Math.random() * 4);
                    this.directions[dir] = !this.directions[dir];
                }
            }
            this.move(time);
        }


        // update monster graphic stats
        switch (this.monsterNumber) {
            case 0: // Ghoul

            case 2: // Undead Warrior
                updateAnimationState(time, 4, 6, 5);
                break;

            case 1: // Imp
                updateAnimationState(time, 5, 5, 10);
                break;

            case 3: // Executioner
                updateAnimationState(time, 4, 6, 6);
                break;

            case 4: // Fire Golem
                updateAnimationState(time, 5, 6, 7);
                break;

            default: // Big Demon
                updateAnimationState(time, 4, 4, 4);
                break;
        }
    }

    private void loadImages(int monsterNumber) {
        this.img = new Image[3][];
        switch (monsterNumber) {
            case 0: // Ghoul
                this.img[0] = new Image[4];
                this.img[1] = new Image[6];
                this.img[2] = new Image[5];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Ghoul/Individual Sprites/ghoul-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Ghoul/Individual Sprites/ghoul-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 5; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Ghoul/Individual Sprites/ghoul-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1: // Imp
                this.img[0] = new Image[5];
                this.img[1] = new Image[5];
                this.img[2] = new Image[10];
                try {
                    for (int i = 0; i < 5; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Imp/Individual Sprites/imp-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 5; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Imp/Individual Sprites/imp-move-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 10; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Imp/Individual Sprites/imp-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2: // Undead Warrior
                this.img[0] = new Image[4];
                this.img[1] = new Image[6];
                this.img[2] = new Image[5];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Undead Warrior/Individual Sprites/undead-warrior-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Undead Warrior/Individual Sprites/undead-warrior-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 5; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Undead Warrior/Individual Sprites/undead-warrior-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                break;

            case 3: // Executioner
                this.img[0] = new Image[4];
                this.img[1] = new Image[6];
                this.img[2] = new Image[6];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Executioner/Individual Sprites/executioner-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Executioner/Individual Sprites/executioner-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Executioner/Individual Sprites/executioner-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                break;

            case 4: // Fire Golem
                this.img[0] = new Image[5];
                this.img[1] = new Image[6];
                this.img[2] = new Image[7];
                try {
                    for (int i = 0; i < 5; i++) {
                        img[0][i] = ImageIO.read(new File("res/monster/Fire Golem/Individual Sprites/fire-golem-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 6; i++) {
                        img[1][i] = ImageIO.read(new File("res/monster/Fire Golem/Individual Sprites/fire-golem-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 7; i++) {
                        img[2][i] = ImageIO.read(new File("res/monster/Fire Golem/Individual Sprites/fire-golem-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                break;

            default: // Big Demon
                this.img = new Image[2][4];
                try {
                    for (int i = 0; i < 4; i++) {
                        img[0][i] = ImageIO.read(new File("res/tiles/big_demon_idle_anim_f" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                    for (int i = 0; i < 4; i++) {
                        img[1][i] = ImageIO.read(new File("res/tiles/big_demon_run_anim_f" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void paint(Graphics g) {
        // paint player
        super.paint(g, 1.2);
    }

    private void setHitting(boolean hitting) {
        isHitting = hitting;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    private void updateAnimationState(int time, int idle, int move, int hit) {
        if (isHitting) {
            this.animation_state += (float) time / 100;
            if (this.animation_state > hit) {
                this.setHitting(false);
            }
        } else if (isMoving) {
            this.animation_state += (float) time / 100;
            this.animation_state %= move;
        } else {
            this.animation_state += (float) time / 150;
            this.animation_state %= idle;
        }
    }

    private double getDistance(int[] p1, int[] p2) {
        return Math.pow(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2), 0.5);
    }

    public void makePath() {
        // A* algorithm
        int[] monsterPos = {this.getVirtualX(), this.getVirtualY()};

        HashMap<int[], Double> openList = new HashMap<>();  // contains f
        HashMap<int[], Double> distanceList = new HashMap<>();  // contains g
        distanceList.put(monsterPos, 0.0);
        HashMap<int[], int[]> ancestor = new HashMap<>();
        ArrayList<int[]> closedList = new ArrayList<>();
        openList.put(monsterPos, 0.0);
        int[] player = {this.map.player.getVirtualX(), this.map.player.getVirtualY()};

        while (!openList.isEmpty()) {
            // get closest node in openlist
            double smallest = Double.MAX_VALUE;
            int[] currentNode = new int[0];
            for (int[] key : openList.keySet()) {
                if (openList.get(key) < smallest) {
                    smallest = openList.get(key);
                    currentNode = key;
                }
            }
            openList.remove(currentNode);

            if (this.map.debugging) {
                this.map.tiles[currentNode[1]][currentNode[0]].isScanned();
            }

            if (Arrays.equals(currentNode, player)) {
                //System.out.println("Player found!!!");


                this.path = new ArrayList<>();
                int[] current = currentNode;
                //System.out.println("Current" + Arrays.toString(current));
                //System.out.println("Monster pos: " + Arrays.toString(monsterPos));
                //System.out.println("Ancestors: ");
                for (int[] p : ancestor.keySet()) {
                    //System.out.println(Arrays.toString(p) + " -> " + Arrays.toString(ancestor.get(p)));
                }
                //System.out.println("Ancestor of player: " + Arrays.toString(ancestor.get(player)));
                while (current != monsterPos) {
                    if (current == null) {
                        //System.out.println("hallo breakpoint");
                    }
                    //this.map.tiles[current[1]][current[0]].isScanned();
                    this.path.add(0, current);
                    //System.out.println("Path: ");
                    for (int[] p : this.path) {
                       // System.out.println("part of path: " + Arrays.toString(p));
                    }
                    current = ancestor.get(current);
                }

                return;
            }

            closedList.add(currentNode);

            for (int i = -1; i <= 1; i++) {
                nextSuccessor:
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    if (i != 0 && j != 0) {
                        //continue;
                    }

                    // can only walk diagonaly if the other both tiles are ground
                    if (i != 0 && !this.map.tile_is_ground(currentNode[0]+i, currentNode[1]) || j != 0 && !this.map.tile_is_ground(currentNode[0], currentNode[1]+j)) {
                        continue;
                    }

                    int x = currentNode[0] + i;
                    int y = currentNode[1] + j;
                    int[] successor = {x, y};
                    if (this.map.tile_is_ground(x, y)) {
                        // check if successor already checked
                        for (int[] p : closedList) {
                            if (Arrays.equals(successor, p)) {
                                // continue with next successor
                                continue nextSuccessor;
                            }
                        }

                        // calculate distance to successors
                        double tentative_g = distanceList.get(currentNode) + (i == 0 || j == 0 ? 1 : Math.pow(2, 0.5));
                        // System.out.println(Arrays.toString(currentNode) + " -> " + Arrays.toString(successor) + " : " + tentative_g);

                        int[] successorInOpenList = null;

                        // check if successor already on openList and the new path is not better than the old one
                        for (int[] p : openList.keySet()) {
                            if (Arrays.equals(successor, p)) {
                                if (tentative_g < distanceList.get(p)) {
                                    successorInOpenList = p;
                                } else {
                                    // continue with next successor
                                    continue nextSuccessor;
                                }
                            }
                        }

                        // if already in openList keep the reference
                        if (successorInOpenList != null) {
                            successor = successorInOpenList;
                        }

                        // set ancestor
                        ancestor.put(successor, currentNode);
                        distanceList.put(successor, tentative_g);

                        // estimate distance to player
                        double f = tentative_g + getDistance(successor, player);

                        // add / update the successor in openList
                        if (Arrays.equals(successor, player)) {
                            //System.out.println("REAL ancestor of successor: " + Arrays.toString(currentNode));
                        }
                        openList.put(successor, f);

                    }
                }
            }
        }
        System.out.println("Player not found!!!");
    }

    private void followPath(int time) {
        int oldX = this.getVirtualX();
        int oldY = this.getVirtualY();

        this.move(time);

        if (oldX != this.getVirtualX() || oldY != this.getVirtualY()) {
            this.map.paintDebug();
        }

        int x = this.getVirtualX();
        int y = this.getVirtualY();

        // directions are only changed if current tile is changed
        if (true) {
            // reset directions
            for (int i = 0; i < 4; i++) {
                this.directions[i] = false;
            }

            // get next point
            //System.out.println("OUR PATH:: " + this.path);
            if (this.path.isEmpty()) {
                System.out.println("im there");
                this.isFollowing = false;
                this.map.followingMonsters.remove(this);
                return;
            }
            int[] next_point = this.path.get(0);
            if (x == next_point[0] && y == next_point[1]) {
                //System.out.println("Changed next point!!!");
                this.path.remove(0);
                // could lead to null pointer
                if (this.path.isEmpty()) {
                    System.out.println("im there too");
                    this.isFollowing = false;
                    this.map.followingMonsters.remove(this);
                    return;
                } else {
                    next_point = this.path.get(0);
                }
            } else {
                //System.out.println("Didnt change next point!!!!");
                //System.out.println("Didnt change next point!!!!");
            }

            // set directions
            if (next_point[0] > x) {
                this.directions[3] = true;
            } else if (next_point[0] < x) {
                this.directions[1] = true;
            }

            if (next_point[1] > y) {
                this.directions[2] = true;
            } else if (next_point[1] < y) {
                this.directions[0] = true;
            }
        }
    }
}

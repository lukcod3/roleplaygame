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
import java.util.List;

public class Monster extends Entity {
    private int exp, monsterNumber, movementTime;
    public boolean isFollowing;
    public ArrayList<List<Integer>> path;

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

    private double getDistance(List<Integer> p1, List<Integer> p2) {
        return Math.pow(Math.pow(p1.get(0) - p2.get(0), 2) + Math.pow(p1.get(1) - p2.get(1), 2), 0.5);
    }

    public boolean makePath() {
        // check if path already calculated
        List<Integer> monsterPos = Arrays.asList(this.getVirtualLeftX(), this.getVirtualY());
        if (this.map.pathsToPlayer.containsKey(monsterPos)) {
            this.path.clear();
            this.path.addAll(this.map.pathsToPlayer.get(monsterPos));
            this.followPathInitial();
        } else {
            // A* algorithm
            HashMap<List<Integer>, Double> openList = new HashMap<>();  // contains f
            HashMap<List<Integer>, Double> distanceList = new HashMap<>();  // contains g
            distanceList.put(monsterPos, 0.0);
            HashMap<List<Integer>, List<Integer>> ancestor = new HashMap<>();
            ArrayList<List<Integer>> closedList = new ArrayList<>();
            openList.put(monsterPos, 0.0);
            List<Integer> player = Arrays.asList(this.map.player.getVirtualLeftX(), this.map.player.getVirtualY());

            while (!openList.isEmpty()) {
                // get closest node in openlist
                double smallest = Double.MAX_VALUE;
                List<Integer> currentNode = null;
                for (List<Integer> key : openList.keySet()) {
                    if (openList.get(key) < smallest) {
                        smallest = openList.get(key);
                        currentNode = key;
                    }
                }

                assert currentNode != null;
                openList.remove(currentNode);

                if (this.map.debugging) {
                    this.map.tiles[currentNode.get(1)][currentNode.get(0)].isScanned();
                }

                if (currentNode.equals(player)) {
                    this.path = new ArrayList<>();
                    while (currentNode != monsterPos) {
                        this.path.add(0, currentNode);
                        currentNode = ancestor.get(currentNode);
                    }

                    if (this.path.isEmpty()) {
                        this.isFollowing = false;
                        return true;
                    }

                    ArrayList<List<Integer>> pathToPlayer = new ArrayList<>(this.path);
                    System.out.println("1: " + this.path);
                    System.out.println("2: " + pathToPlayer);
                    this.map.pathsToPlayer.put(monsterPos, pathToPlayer);
                    this.followPathInitial();
                    return false;
                }

                closedList.add(currentNode);

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        // can only walk diagonaly if the other both tiles are ground
                        if (i != 0 && !this.map.tile_is_ground(currentNode.get(0) + i, currentNode.get(1)) || j != 0 && !this.map.tile_is_ground(currentNode.get(0), currentNode.get(1) + j)) {
                            continue;
                        }

                        int x = currentNode.get(0) + i;
                        int y = currentNode.get(1) + j;
                        List<Integer> successor = Arrays.asList(x, y);
                        if (this.map.tile_is_ground(x, y)) {
                            // check if successor already checked
                            if (closedList.contains(successor)) {
                                // continue with next successor
                                continue;
                            }

                            // calculate distance to successors
                            double tentative_g = distanceList.get(currentNode) + (i == 0 || j == 0 ? 1 : Math.pow(2, 0.5));
                            // System.out.println(Arrays.toString(currentNode) + " -> " + Arrays.toString(successor) + " : " + tentative_g);

                            int[] successorInOpenList = null;

                            // check if successor already on openList and the new path is not better than the old one
                            if (openList.containsKey(successor) && tentative_g > distanceList.get(successor)) {
                                // continue with next successor
                                continue;
                            }

                            // set ancestor
                            ancestor.put(successor, currentNode);
                            distanceList.put(successor, tentative_g);

                            // estimate distance to player
                            double f = tentative_g + getDistance(successor, player);

                            openList.put(successor, f);

                        }
                    }
                }
            }
            System.out.println("Player not found!!!");
        }
        System.out.println("oh nein");
        return false;
    }

    private void followPathInitial() {
        // reset directions
        for (int i = 0; i < 4; i++) {
            this.directions[i] = false;
        }

        List<Integer> next_point = this.path.get(0);
        this.setDirections(next_point);
    }

    private void setDirections(List<Integer> point) {
        int leftX = this.getVirtualLeftX();
        int rightX = this.getVirtualLeftX();
        int y = this.getVirtualY();

        if (point.get(0) > leftX) {
            this.directions[3] = true;
        } else if (point.get(0) < rightX) {
            this.directions[1] = true;
        }

        if (point.get(1) > y) {
            this.directions[2] = true;
        } else if (point.get(1) < y) {
            this.directions[0] = true;
        }
    }

    private void followPath(int time) {
        int oldLeftX = this.getVirtualLeftX();
        int oldRightX = this.getVirtualRightX();
        int oldY = this.getVirtualY();

        this.move(time);

        int leftX = this.getVirtualLeftX();
        int rightX = this.getVirtualLeftX();
        int y = this.getVirtualY();

        // directions are only changed if current tile is changed
        //if (oldX != this.getVirtualLeftX() || oldY != this.getVirtualY()) {
        if (this.directions[1] && rightX != oldRightX || this.directions[3] && leftX != oldLeftX || !(this.directions[1] || this.directions[3]) && oldY != y) {
            if (this.map.debugging) {
                this.map.paintDebug();
            }
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
            List<Integer> next_point = this.path.get(0);
            if (x == next_point.get(0) && y == next_point.get(1)) {
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
            }

            // set directions
            this.setDirections(next_point);
        }
    }
}

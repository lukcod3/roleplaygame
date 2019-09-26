import java.awt.*;

class Player {
    private int x, y;
    private int width, height;
    private float speed = 1;
    boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right

    Player(int x, int y) {
        // setup player stats
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 100;
        this.directions = new boolean[4];
    }

    void paint(Graphics g) {
        // paint player
        g.setColor(Color.BLACK);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

    void update(int time) {
        // count how many directions are active
        int dir_count = 0;
        for (boolean b : this.directions) {
            if (b) {
                dir_count++;
            }
        }

        // if no or opposite keys are pressed the player doesnt move
        if (dir_count == 0 || dir_count > 2 || this.directions[0] && this.directions[2] || this.directions[1] && this.directions[3]) {
            // opposite keys are pressed => player doesnt move
            return;
        }

        // calculate how much the player moves
        double movement = this.speed * time;

        // if the player moves in 2 directions he doesnt run faster
        if (dir_count == 2) {
            movement *= 1.0 / Math.pow(2, 0.5);
        }

        // move player
        if (this.directions[0]) {
            this.y -= movement;
        }
        if (this.directions[1]) {
            this.x -= movement;
        }
        if (this.directions[2]) {
            this.y += movement;
        }
        if (this.directions[3]) {
            this.x += movement;
        }
    }

}

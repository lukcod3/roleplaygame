
import java.awt.*;

class Player {
    private int x, y;
    private int width, height;

    Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 100;
    }

    void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

}

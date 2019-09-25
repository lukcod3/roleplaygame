import javax.swing.*;
import java.awt.*;

class GUI extends JPanel{
    private Player player;
    GUI() {
        player = new Player(0,0);
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0,0,960, 540);

        player.paint(g);

        Toolkit.getDefaultToolkit().sync();
    }
}

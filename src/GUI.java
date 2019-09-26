import javax.swing.*;
import java.awt.*;

class GUI extends JPanel{
    private Player player;

    GUI() {
	// create the player
        player = new Player(0,0);
    }

    public void paint(Graphics g) {
	// paint background
        g.setColor(Color.RED);
        g.fillRect(0,0,960, 540);

	// paint graphic objects
        player.paint(g);

	// sync graphic
        Toolkit.getDefaultToolkit().sync();
    }
}

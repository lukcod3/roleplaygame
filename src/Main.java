import javax.swing.*;

public class Main extends JPanel{
    private Main() {
	// setup main frame
        JFrame frame = new JFrame();
        frame.setTitle("RPG-lol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(960, 540);

	// measure time for game logic
        long time0 = System.currentTimeMillis();

	// main game jpanel
        JPanel gui = new GUI();
        frame.add(gui);

	// updating the game
        while(true) {
            try {
		// wait 16ms
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            // measure time for updating game logic
            long time1 = System.currentTimeMillis();
            updateGame((int) (time1-time0));
            time0 = time1;

            // update the frame
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    private void updateGame(int time) {
	
    }
}

import javax.swing.*;

public class Main extends JPanel{
    private Main() {
        JFrame frame = new JFrame();
        frame.setTitle("RPG-lol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(960, 540);

        long time0 = System.currentTimeMillis();

        JPanel gui = new GUI();

        frame.add(gui);

        while(true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            long time1 = System.currentTimeMillis();
            updateGame((int) (time1-time0));
            time0 = time1;
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    private void updateGame(int time) {
	
    }
}

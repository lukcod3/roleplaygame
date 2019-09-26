package Maps;

import java.awt.*;

public abstract class Map {
    int width, height;

    public abstract void paint(Graphics g);

    public abstract void update(int time);

}

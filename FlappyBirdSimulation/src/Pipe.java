
import java.awt.*;

public class Pipe {
    public int x, width = 60, gapY, gapHeight = 150;

    public Pipe(int startX) {
        this.x = startX;
        this.gapY = 100 + (int)(Math.random() * 300);
    }

    public void update() {
        x -= 4;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, 0, width, gapY);
        g.fillRect(x, gapY + gapHeight, width, 600);
    }

    public boolean collidesWith(int birdX, double birdY) {
        if (birdX + 20 > x && birdX < x + width) {
            return birdY < gapY || birdY > gapY + gapHeight;
        }
        return false;
    }
}


import java.awt.*;

public class Bird {
    int y, velocity;
    Genome genome;
    boolean isAlive;
    public int fitness = 0;
    public final int x = 200;

    public Bird(Genome genome, int startY) {
        this.y = startY; // ✅ spawn at the pipe gap
        this.velocity = 0;
        this.isAlive = true;
        this.genome = genome;
    }

    public void update(Pipe nextPipe){
        if(!isAlive) return;


        if(collidesWith(nextPipe)){
            isAlive = false;
        }

        double dy = nextPipe.gapY - this.y;
        double dx = nextPipe.x - this.x;

        if(genome.shouldFlap(dy, dx, velocity)){
            velocity = -9;
        }

        velocity += 2;
        y+= velocity;
        fitness++;

    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();

        // Set different opacity based on alive status
        float alpha = isAlive ? 0.6f : 0.1f; // 60% for alive, 10% for dead
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Set color (optional: color-code best bird separately)
        g2d.setColor(Color.BLACK);

        // Draw bird
        g2d.fillOval(x, (int) y, 20, 20);

        g2d.dispose();
    }

    public boolean collidesWith(Pipe pipe) {
        return pipe.collidesWith(x, y);
    }

}

import java.awt.*;
import java.util.*;
import javax.swing.*;


public class gamePanel extends JPanel implements Runnable {
     int width = 800;
     int height = 600;

    ArrayList<Pipe> pipes = new ArrayList<>();
    ArrayList<Bird> birds = new ArrayList<>();
    int generation = 0;
    Random random = new Random();
    private double score = 0;

    /*
    Preconditions: No arguments
    Postconditions: sets up the game
     */
    public gamePanel() {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setBackground(Color.WHITE);
        initPopulation();
        new Thread(this).start();


    }

    public void initPopulation(){
        birds.clear();
        pipes.clear();
        Pipe firstPipe = new Pipe(800);
        pipes.add(firstPipe);
        double gapY = firstPipe.gapY;
        for(int i = 0; i < 50; i++){
            birds.add(new Bird(Genome.randomeGenome(), (int) gapY));
        }

    }

    public void run() {
        long lastTime = System.nanoTime();
        while (true) {
            updateGame();
            repaint();
            try { Thread.sleep(32); } catch (Exception e) {}
        }
    }

    public void updateGame(){
        Pipe nextPipe = goNextPipe();
        for(Bird b : birds){
            if(b.isAlive){
                b.update(nextPipe);
                if(b.collidesWith(nextPipe) || b.y > 600 || b.y < 0){
                    b.isAlive = false;
                }
            }
        }

        updatePipes();

        if(allBirdsDead()){
            evolve();
        }
    }


    public void updatePipes(){
        Iterator<Pipe> iter = pipes.iterator();
        while (iter.hasNext()) {
            Pipe p = iter.next();
            p.update(); // moves the pipe left
            if (p.x + p.width < 0) {
                iter.remove(); // pipe offscreen
            }
        }

        // Add new pipes at intervals
        if (pipes.get(pipes.size() - 1).x < 400) {
            pipes.add(new Pipe(800)); // or randomized gapY
        }
    }


    public boolean allBirdsDead() {
        for (Bird b : birds) {
            if (b.isAlive) return false;
        }
        return true;
    }

    public Pipe goNextPipe(){
        return pipes.stream().filter(p -> p.x + p.width > 200).findFirst().orElse(pipes.get(0));
    }

    public void evolve(){
        generation++;
        score++;
        birds.sort(Comparator.comparingInt(b -> -b.fitness));
        ArrayList<Bird> nextGen = new ArrayList<>();
        Pipe nextPipe = new  Pipe(800);

        for(int i = 0; i < 10; i++){
            Bird parent = birds.get(i);
            for(int j = 0; j < 5; j++){
                Genome g = mutate(cloneGenome(parent.genome));
                nextGen.add(new Bird(g, nextPipe.gapY));
            }
        }

        birds = nextGen;
    }

    public Genome cloneGenome(Genome genome){
        return new Genome(genome.wDy, genome.wDx, genome.wVel, genome.threshold);
    }

    public Genome mutate(Genome g) {
        double m = 0.1;
        if (Math.random() < m) g.wDy += Math.random() * 0.2 - 0.1;
        if (Math.random() < m) g.wDx += Math.random() * 0.2 - 0.1;
        if (Math.random() < m) g.wVel += Math.random() * 0.2 - 0.1;
        if (Math.random() < m) g.threshold += Math.random() * 0.2 - 0.1;
        return g;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Pipe p : pipes) p.draw(g);
        for (Bird b : birds) b.draw(g);

        g.setColor(Color.BLACK);
        g.drawString("Generation: " + generation, 10, 20);
        g.drawString("Score: " + score, 10, 30);
    }




}

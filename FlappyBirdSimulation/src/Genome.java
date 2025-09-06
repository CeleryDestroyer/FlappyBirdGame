public class Genome {
    double wDy, wDx, wVel, threshold;
    public Genome(double wDy, double wDx, double wVel, double threshold) {
        this.wDy = wDy;
        this.wDx = wDx;
        this.wVel = wVel;
        this.threshold = threshold;

    }
    public static Genome randomeGenome(){
        return new Genome(Math.random()*2-1, Math.random()*2-1, Math.random()*2-1, Math.random()*2-1);
    }

    public boolean shouldFlap(double dy, double dx, double velocity){
        double sum = dy * wDy + dx * wDx + velocity * wVel;
        return sum > threshold;
    }
}


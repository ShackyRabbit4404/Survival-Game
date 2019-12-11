public class Hitbox{
    private double[][] points;
    public Hitbox(double[][] p){
        points = p;
    }
    public boolean contains(double[][] pts,double xShift, double yShift){
        for(double[] pt: pts){
            if(pt[0]+xShift > points[0][0] && pt[0]+xShift < points[1][0] && pt[1]+yShift > points[0][1] && pt[1]+yShift < points[1][1])
                return true;
        }
        return false;
    }
    public double[][] getPoints(){
        return points;
    }
}
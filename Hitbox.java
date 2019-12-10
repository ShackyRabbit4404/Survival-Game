public class Hitbox{
    private double[][] points;
    public Hitbox(double[][] p){
        points = p;
    }
    public boolean contains(double[][] pts,double xShift, double yShift){
        for(int i = 0; i < pts.length; i+=2){
            if(pts[i][0]+xShift > points[0][0] && pts[i][0]+xShift < points[1][0] && pts[i][1]+yShift > points[0][1] && pts[i][1]+yShift < points[1][1])
                return true;
        }   
        return false;
    }
    public double[][] getPoints(){
        return points;
    }
}
public class Player{
    private double[] cords;
    private int viewRange;
    private double movementSpeed;
    private int[] dimentions;
    public Player(double x, double y,int vr,double ms){
        cords = new double[]{x,y};
        viewRange = vr;
        //blocks per second
        movementSpeed = ms;
        dimentions = new int[]{1,1};
    }
    public int getViewRange(){
        return viewRange;
    }
    public void moveVertically(double delay){
        cords[1] += movementSpeed*delay;
    }
    public void moveHorizontally(double delay){
        cords[0] += movementSpeed*delay;
    }
    public double[] getCords(){
        return cords;
    }
}
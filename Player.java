public class Player{
    private double[] cords;
    private int viewRange;
    private double movementSpeed;
    private int[] dimentions;
    private double jumpForce;
    private double gravity;
    private double verticalVelocity;
    private boolean grounded;
    public Player(double x, double y,int vr,double ms,double jf,double g,double vv){
        cords = new double[]{x,y};
        viewRange = vr;
        //blocks per second
        movementSpeed = ms;
        dimentions = new int[]{1,1};
        jumpForce = jf;
        gravity = g;
        verticalVelocity = vv;
        grounded = false;
    }
    public void setVertVelocity(double vv){
        verticalVelocity = vv;
    }
    //removes the decimal places on the y cordinate
    public void intVertical(boolean a){
        verticalVelocity = 0;
        if(a)
            cords[1] = (double)((int)(cords[1]))-.000001;
        else
            cords[1] = (double)((int)(cords[1]+1))+.000001;
    }
    //removes the decimal places on the y cordinate
    //+.000001
    public boolean getGrounded(){
        return grounded;
    }
    public void jump(){
        verticalVelocity = jumpForce*-1;
    }
    public double getVertVelocity(){
        return verticalVelocity;
    }
    public void intHorizontal(boolean a){
        if(a)
            cords[0] = (double)((int)(cords[0]))-.000001;
        else
            cords[0] = (double)((int)(cords[0]+1))+.000001;
    }
    public void land(){
        grounded = true;
        verticalVelocity = 0;
    }
    public void falling(double delay){
        grounded = false;
        moveVertically(delay,false);
    }
    public int getViewRange(){
        return viewRange;
    }
    public void applyGravity(double delay){
        verticalVelocity += gravity*delay;
        cords[1] += verticalVelocity*delay;
    }
    public void moveVertically(double delay,boolean jump){
        cords[1] += movementSpeed*delay;
        /*
        if(jump){
            verticalVelocity = jumpForce*-1;
        }
        verticalVelocity += gravity*delay;
        cords[1] += verticalVelocity*delay;
        */
    }
    public double[] getVerticalMove(double delay){
        return new double[]{cords[0],cords[1]+movementSpeed*delay};
    }
    public void moveHorizontally(double delay){
        cords[0] += movementSpeed*delay;
    }
    public double[] getHorizontalMove(double delay){
        return new double[]{cords[0]+movementSpeed*delay,cords[1]};
    }
    public double[] getCords(){
        return cords;
    }
    public int[] getDimentions(){
        return dimentions;
    }
}
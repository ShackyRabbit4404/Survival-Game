public class Player{
    private double[] cords;
    private int viewRange;
    private double movementSpeed;
    private int[] dimentions;
    private double jumpForce;
    private double gravity;
    private double verticalVelocity;
    private boolean grounded;
    private Hitbox hitbox;
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
        genHitbox();
    }
    public void genHitbox(){
        double[][] points = new double[dimentions[0]*4+dimentions[1]*4][2];
        System.out.println("Player num hitbox points: "+points.length);
        int count = 0;
        for(double x = 0; x <= dimentions[0]; x+=0.5){
            points[(int)(x*2)] = new double[]{x,0};
            points[(int)(x*2)+dimentions[0]*2+1] = new double[]{x,dimentions[1]};
            count += 2;
        }
        System.out.println((dimentions[0]*4+2)+" should be equal to 10");
        for(double y = 0.5; y < dimentions[1];y+=0.5){
            points[(int)((y-.5)*2)+dimentions[0]*4+2] = new double[]{0,y};
            points[(int)((y-.5)*2)+dimentions[0]*4+2+dimentions[1]*2-1] = new double[]{dimentions[0],y};
            count += 2;
        }
        System.out.println(count+" should equal "+points.length);
        hitbox = new Hitbox(points);
        for(double[] cord:points){
            System.out.println("Player hitbox -> X: "+cord[0]+" Y: "+cord[1]);
        }
    }
    public void setVertVelocity(double vv){
        verticalVelocity = vv;
    }
    //removes the decimal places on the y cordinate
    public void intVertical(boolean a){
        verticalVelocity = 0;
        if(a)
            cords[1] = (double)((int)(cords[1]));
        else
            cords[1] = (double)((int)(cords[1]+1));
    }
    //removes the decimal places on the y cordinate
    //+.000001
    public Hitbox getHitbox(){
        return hitbox;
    }
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
            cords[0] = (double)((int)(cords[0]));
        else
            cords[0] = (double)((int)(cords[0]+1));
    }
    public void land(){
        grounded = true;
        verticalVelocity = 0;
    }
    public void falling(){
        grounded = false;
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
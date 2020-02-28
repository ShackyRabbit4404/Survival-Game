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
    private Item[] inventory;
    private Item[] hotbar;
    private int hotbarItemSelected;
    private double health;
    private double maxHealth;
    private boolean isAlive; 
    private int sightRange;
    //constructor method
    public Player(double x, double y,int vr,double ms,double jf,double g,double vv){
        cords = new double[]{x,y};
        viewRange = vr;
        //blocks per second
        movementSpeed = ms;
        dimentions = new int[]{2,3};
        jumpForce = jf;
        gravity = g;
        verticalVelocity = vv;
        grounded = false;
        genHitbox();
        inventory = new Item[20];
        hotbar = new Item[10];
        hotbarItemSelected = 0;
        isAlive = true;
        sightRange = 15;
    }
    //generates the hit box for the player and it is modular to any player dimentions
    public void genHitbox(){
        double[][] points = new double[dimentions[0]*4+dimentions[1]*4][2];
        int count = 0;
        for(double x = 0; x <= dimentions[0]; x+=0.5){
            points[(int)(x*2)] = new double[]{x,0};
            points[(int)(x*2)+dimentions[0]*2+1] = new double[]{x,dimentions[1]};
            count += 2;
        }
        for(double y = 0.5; y < dimentions[1];y+=0.5){
            points[(int)((y-.5)*2)+dimentions[0]*4+2] = new double[]{0,y};
            points[(int)((y-.5)*2)+dimentions[0]*4+2+dimentions[1]*2-1] = new double[]{dimentions[0],y};
            count += 2;
        }
        hitbox = new Hitbox(points);
    }
    //sets the vertical velocity to a given value
    public void setVertVelocity(double vv){
        verticalVelocity = vv;
    }
    //adds items to inventory
    public void addItem(Item a){
        boolean found = false;
        for(Item i: inventory){
            if(i != null && i.getName().equals(a.getName())){
                int addable = i.canStackMore();
                if(addable >= a.getCount()){
                    i.increaseStack(a.getCount());
                    found = true;
                }
                else{
                    i.increaseStack(addable);
                    a.decreaseStack(addable);
                }
            }
        }
        if(!found){
            int i = 0;
            while(i < inventory.length-1 && inventory[i] != null){
                i++;
            }
            inventory[i] = a;
        }
        if(hotbar[hotbarItemSelected] == null){
            hotbar[hotbarItemSelected] = a;
        }
    }
    public boolean isAlive(){
        return isAlive;
    }
    //removes a specifc amount of an item from the inventory
    public void removeItem(Item i, int quantity){
        i.decreaseStack(quantity);
        if(i.getCount() <= 0){
            i = null;
        }
    }
    public double[] getTrueLoc(){
        return new double[]{cords[0]+(dimentions[0]/2.0),cords[1]+(dimentions[1]/2.0)};
    }
    //sets which item you are using in your hotbar
    public void setHotBarItemSelected(int hbis){
        hotbarItemSelected = hbis;
    }
    public double getHealth(){
        return health;
    }
    public void changeHealth(double hc){
        health += hc;
        if(health <= 0){
            die();
        }
    }
    public void die(){
        isAlive = false;
    }
    //removes the decimal places on the y cordinate
    public void intVertical(boolean a){
        verticalVelocity = 0;
        if(a)
            cords[1] = (double)((int)(cords[1]));
        else
            cords[1] = (double)((int)(cords[1]+1));
    }
    //returns the player inventory
    public Item[] getInventory(){
        return inventory;
    }
    //returns the hitbox of the player
    public Hitbox getHitbox(){
        return hitbox;
    }
    //returns wether the player is grounded
    public boolean getGrounded(){
        return grounded;
    }
    //get the sight range
    public int getSightRange(){
        return sightRange;
    }
    //sets the vertical velocity to the jump force
    public void jump(){
        verticalVelocity = jumpForce*-1;
    }
    //returns the vertical velocity 
    public double getVertVelocity(){
        return verticalVelocity;
    }
    //when the player has a one block step up, the program will auto do that
    public void stepUp(){
        jump();
        //cords[1] -= 1.1;
    }
    //removes the decimal place on the x cordinate
    public void intHorizontal(boolean a){
        if(a)
            cords[0] = (double)((int)(cords[0]));
        else
            cords[0] = (double)((int)(cords[0]+1));
    }
    //adjust varibles to react when the player hits the ground
    public void land(){
        grounded = true;
        verticalVelocity = 0;
    }
    //adjust varibles to react when the player is falling
    public void falling(){
        grounded = false;
    }
    //returns how far to the left and right the player can see
    public int getViewRange(){
        return viewRange;
    }
    //adjusts the vertical velocity to account for gravity and alters the y value based on that
    public void applyGravity(double delay){
        verticalVelocity += gravity*delay;
        cords[1] += verticalVelocity*delay;
    }
    //moves the player in the y direction
    public void moveVertically(double delay,boolean jump){
        cords[1] += movementSpeed*delay;
    }
    //gets the predicted vertical move without actually adjusting the player's y position
    public double[] getVerticalMove(double delay){
        return new double[]{cords[0],cords[1]+movementSpeed*delay};
    }
    //moves the players horizontal position
    public void moveHorizontally(double delay){
        cords[0] += movementSpeed*delay;
    }
    //returns the hotbar
    public Item[] getHotbar(){
        return hotbar;
    }
    //returns the hotbar item that is selected
    public int getHotbarItemSelected(){
        return hotbarItemSelected;
    }
    //gets the predicted horizontal move without actually adjusting the player's x position
    public double[] getHorizontalMove(double delay){
        return new double[]{cords[0]+movementSpeed*delay,cords[1]};
    }
    //returns the player cords
    public double[] getCords(){
        return cords;
    }
    //returns the dimentions of the player
    public int[] getDimentions(){
        return dimentions;
    }
}
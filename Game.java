import java.util.*;
import java.awt.*;
public class Game{
    //0 = main menu, 1 = player view, 2 = map view,
    private int screenNum;
    //0 = empty, 1 = grassy dirt, 2 = dirt, 3 = stone
    private int[][] world;
    private int[][] playerView;
    private double[] viewBoxCords;
    private double hillIntensity;
    private double caveIntensity;
    private double caveWallThickness;
    private double seed;
    private int screenWidth;
    private int screenHeight;
    private double playerViewScale;
    private Player player;
    private double gravity;
    private double verticalVelocity;
    private double jumpForce;
    //0 = up, 1 = down, 2 = right, 3 = left
    private boolean[] keys;
    public int screenScale;
    //constructor method
    public Game(int w, int h,double hi,double ci,double cwt,int sw,int sh,int ss){
        screenNum = 2;
        world = new int[1][1];
        hillIntensity = hi;
        caveIntensity = ci;
        caveWallThickness = cwt;
        generateWorld(w,h);
        //num blocks the player can see in any direction
        int playerViewRange = 20;
        //blocks per second
        double playerSpeed = 10;
        gravity = 20;
        verticalVelocity = 0;
        jumpForce = 12;
        player = new Player(w/2,h/8,playerViewRange,playerSpeed,jumpForce,gravity,verticalVelocity);
        keys = new boolean[4];
        screenWidth = sw;
        screenHeight = sh;
        playerViewScale = screenWidth/((double)playerViewRange*2+player.getDimentions()[0]);
        int blocksUp = (int)((((double)screenHeight/playerViewScale)-player.getDimentions()[1])/2.0+0.5);
        System.out.println("Player block scale: "+playerViewScale);
        playerView = new int[playerViewRange*2+player.getDimentions()[0]+1][blocksUp*2+player.getDimentions()[1]+1];
        viewBoxCords = new double[2];
        screenScale = ss;
    }
    //generates the word including cave systems
    public void generateWorld(int w, int h){
        Noise noise = new Noise();
        world = new int[w][h];
        seed = 1000000*Math.random();
        System.out.println("Hill Intensity: "+hillIntensity+" Cave Intensity: "+caveIntensity);
        //generates screen terrain based on the seed
        for(double x = 0; x < w; x++){
            int temp = (int)(((noise.noise((x/hillIntensity)+seed)+1)/2)*(double)(h/2));
            world[(int)x][temp] = 1;
            for(int y = temp+1; y < world[0].length; y++){
                if(((double)y)/((double)world[0].length) > Math.random()/10+0.35){
                    world[(int)x][y] = 3;
                }
                else{
                    world[(int)x][y] = 2;
                }
            }
        }
        //generates the cave systems based off of the seed
        for(int x = 0; x < world.length; x++){
            for(int y = 0; y < world[0].length; y++){
                if(noise.noise(((double)x/caveIntensity)+seed,(double)y/caveIntensity) < caveWallThickness && noise.noise(((double)x/caveIntensity)+seed,(double)y/caveIntensity) > 0.08){
                    world[x][y] = 0;
                }
            }
        }
    }
    //updates the game values (player position, and user input)
    public void update(double delay){
        if(keys[0] && player.getGrounded() && player.getCords()[1] > 0){
            player.jump();
        }
        /*
        if(keys[1] && player.getCords()[1] < world[0].length-player.getDimentions()[1]){
            player.moveVertically(delay,false);
            if(collides(player.getCords())){
                player.intVertical(true);
            }
        }
        */
        if(keys[2] && player.getCords()[0] < world.length-player.getDimentions()[0]){
            player.moveHorizontally(delay);
            if(collides(player.getCords())){
                if(player.getGrounded() && world[(int)player.getCords()[0]+player.getDimentions()[0]][(int)player.getCords()[1]+player.getDimentions()[1]-2] == 0){
                    player.stepUp();
                }
                player.intHorizontal(true);
            }
        }
        if(keys[3] && player.getCords()[0] > 0){
            player.moveHorizontally(delay*-1);
            if(collides(player.getCords())){
                if(player.getGrounded() && world[(int)player.getCords()[0]][(int)player.getCords()[1]+player.getDimentions()[1]-2] == 0){
                    player.stepUp();
                }
                player.intHorizontal(false);
            }
        }
        player.applyGravity(delay);
        if(collides(player.getCords())){
            if(player.getVertVelocity() > 0){
                player.intVertical(true);
                player.land();
            }
            else{
                player.setVertVelocity(0);
                player.intVertical(false);
            }
        }
        else{
            player.falling();
        }   
        setPlayerView();
    }
    //sets the view of the player based on view range
    public void setPlayerView(){
        viewBoxCords = new double[]{player.getCords()[0]-player.getViewRange(),player.getCords()[1]-(playerView[0].length-player.getDimentions()[1])/2};
        if(viewBoxCords[0] < 0){
            viewBoxCords[0] = 0;
        }
        else if(viewBoxCords[0] > world.length-playerView.length-player.getDimentions()[0]){
            viewBoxCords[0] = world.length-playerView.length;
        }
        if(viewBoxCords[1] < 0){
            viewBoxCords[1] =  0;
        }
        else if(viewBoxCords[1] > world[0].length-playerView[0].length-player.getDimentions()[1]){
            viewBoxCords[1] = world[0].length-playerView[0].length;
        }
        for(int x = 0; x < playerView.length;x++){
            for(int y = 0; y < playerView[0].length;y++){
                playerView[x][y] = world[(int)(viewBoxCords[0])+x][(int)(viewBoxCords[1])+y];
            }
        }
    }
    //checks if the player is colliding with anything
    public boolean collides(double[] cords){
        if(cords[1] < 0 || cords[0] < 0 || cords[0] > world.length-player.getDimentions()[0]-1 || cords[1] > world[0].length-player.getDimentions()[1]-1)
            return true;
        ArrayList<Hitbox> boxes = new ArrayList<Hitbox>();
        for(int x = (int)cords[0]; x <= (int)(cords[0]+player.getDimentions()[0]);x++){
            for(int y = (int)cords[1]; y <= (int)(cords[1]+player.getDimentions()[1]);y++){
                if(world[x][y] != 0 && new Hitbox(new double[][]{{x,y},{x+1,y+1}}).contains(player.getHitbox().getPoints(),player.getCords()[0],player.getCords()[1])){
                    return true;
                }
            }
        }
        return false;
    }
    //reacts to when and where the player clicks
    public void clicked(int x, int y, int clickNum){
        System.out.println("Width: "+playerView.length+" Height: "+playerView[0].length);
        if(screenNum == 1){
            System.out.println("Before scaled down X: "+x+" Y: "+y);
            x = (int)((x+((viewBoxCords[0]%1)*playerViewScale))/playerViewScale);
            y = (int)((y-29+((viewBoxCords[1]%1)*playerViewScale))/playerViewScale);
            System.out.println("After scaled down X:"+x+" Y: "+y);
            if(playerView[x][y] != 0){
                if(playerView[x][y] == 1){
                    System.out.println("Removing grassy dirt");
                    player.addItem(new Item("Dirt",1));
                }
                else if(playerView[x][y] == 2){
                    System.out.println("Removing dirt");
                    player.addItem(new Item("Dirt",1));
                }
                else if(playerView[x][y] == 3){
                    System.out.println("Removing stone");
                    player.addItem(new Item("Stone",1));
                }
                world[(int)viewBoxCords[0]+x][(int)viewBoxCords[1]+y] = 0;
            }
        }
    }
    //returns screenNum (what view the display class is showing)
    public void setScreenNum(int sn){
        screenNum = sn;
    }
    //returns the players view
    public int[][] getPlayerView(){
        return playerView;
    }
    //returns the location of the players view
    public double[] getViewBoxCords(){
        return viewBoxCords;
    }
    //returns the players dimentions
    public int[] getPlayerDimentions(){
        return player.getDimentions();
    }
    //returns the player view range
    public int getPlayerViewRange(){
        return player.getViewRange();
    }
    //returns the player view scale
    public double getPlayerViewScale(){
        return playerViewScale;
    }
    //returns wether the player moving up key is pressed
    public void movingUp(boolean up){
        keys[0] = up;
    }
    //returns if the player moving down key is pressed
    public void movingDown(boolean down){
        keys[1] = down;
    }
    //returns if the player moving right key is pressed
    public void movingRight(boolean right){
        keys[2] = right;
    }
    //returns if the player moving left key is pressed
    public void movingLeft(boolean left){
        keys[3] = left;
    }
    //returns the view that the display class shows
    public int getWindowNum(){
        return screenNum;
    }
    //returns the world 
    public int[][] getWorld(){
        return world;
    }
    //returns the player cordinates
    public double[] getPlayerCords(){
        return player.getCords();
    }
    //return the seed of the world
    public double getSeed(){
        return seed;
    }
    //returns the player object
    public Player getPlayer(){
        return player;
    }
    //returns the player's inventory
    public Item[] getPlayerInventory(){
        return player.getInventory();
    }
}
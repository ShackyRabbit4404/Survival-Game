import java.util.*;
import java.awt.*;
public class Game{
    //0 = main menu, 1 = player view, 2 = map view,
    private int screenNum;
    //0 = empty, 1 = dirt, 2 = stone, 3 = bedrock
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
    public Game(int w, int h,double hi,double ci,double cwt,int sw,int sh){
        screenNum = 2;
        world = new int[1][1];
        hillIntensity = hi;
        caveIntensity = ci;
        caveWallThickness = cwt;
        generateWorld(w,h);
        //num blocks the player can see in any direction
        int playerViewRange = 20;
        //blocks per second
        double playerSpeed = 20;
        gravity = 20;
        verticalVelocity = 0;
        jumpForce = 8;
        player = new Player(w/2,h/8,playerViewRange,playerSpeed,jumpForce,gravity,verticalVelocity);
        keys = new boolean[4];
        screenWidth = sw;
        screenHeight = sh;
        playerViewScale = screenWidth/((double)playerViewRange*2+player.getDimentions()[0]);
        int blocksUp = (int)((((double)screenHeight/playerViewScale)-player.getDimentions()[1])/2.0+0.5);
        System.out.println("Player block scale: "+playerViewScale);
        playerView = new int[playerViewRange*2+player.getDimentions()[0]+1][blocksUp*2+player.getDimentions()[1]+1];
        viewBoxCords = new double[2];
    }
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
    public void update(double delay){
        if(keys[0] && player.getCords()[1] > 0){
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
            if(collides2()){
                player.intHorizontal(true);
            }
        }
        if(keys[3] && player.getCords()[0] > 0){
            player.moveHorizontally(delay*-1);
            if(collides2()){
                player.intHorizontal(false);
            }
        }
        player.applyGravity(delay);
        if(collides2()){
            player.setVertVelocity(0);
            if(player.getVertVelocity() < 0)
                player.intVertical(false);
            else
                player.intVertical(true);
        }
        setPlayerView();
    }
    public boolean solidPoint(int x, int y){
        if(x == 0 && (world[x][y-1] != 0 || world[x][y] != 0)){
            return true;
        }
        else if(y == 0 && (world[x][y] != 0 || world[x-1][y] != 0)){
            return true;
        }
        else if(world[x][y] != 0 || world[x-1][y] != 0 || world[x-1][y-1] != 0 || world[x][y-1] != 0){
            return true;
        }
        return false;
    }
    /*
    if(vert && tempCords[0]%1 == 0 && (y > tempCords[1]  || y < tempCords[1]+player.getDimentions()[1])){
                    return true;
                }
                else if(!vert && tempCords[1]%1 == 0 && (x > tempCords[0] || x < tempCords[1]+player.getDimentions()[0])){
                    return true;
                }
                */
    public boolean collides(double[] tempCords){
        for(double x = (int)tempCords[0]; x <= (int)(tempCords[0]+player.getDimentions()[0]);x+=0.5){
            for(double y = (int)tempCords[1]; y <= (int)(tempCords[1]+player.getDimentions()[1]); y+=0.5){
                if(solidPoint((int)x,(int)y) && x > tempCords[0] && x < tempCords[0]+player.getDimentions()[0] && y > tempCords[1] && y < tempCords[1]+player.getDimentions()[1]){
                    return true;
                }
            }
        }
        return false;
    }
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
    public boolean collides2(){
        ArrayList<Hitbox> boxes = new ArrayList<Hitbox>();
        for(int x = (int)player.getCords()[0]; x < (int)(player.getCords()[0]+player.getDimentions()[0]);x++){
            for(int y = (int)player.getCords()[1]; y < (int)(player.getCords()[1]+player.getDimentions()[1]);y++){
                boxes.add(new Hitbox(new double[][]{{x,y},{x+1,y+1}}));
            }
        }
        for(int i = 0; i < boxes.size(); i++){
            if(boxes.get(i).contains(player.getHitbox().getPoints(),player.getCords()[0],player.getCords()[1])){
                return true;
            }
        }
        /*
        for(double x = (int)player.getCords()[0]; x < (int)(player.getCords()[0]+player.getDimentions()[0]); x+=0.5){
            for(double y = (int)player.getCords()[1]; y < (int)(player.getCords()[1]+player.getDimentions()[1]);y+=0.5){
                if(solidPoint((int)x,(int)y) && x > player.getCords()[0] && x < player.getCords()[0]+player.getDimentions()[0] && y > player.getCords()[1] && y < player.getCords()[1]+player.getDimentions()[1]){
                    return true;
                }
            }
        }
        */
        return false;
    }
    
    public void setScreenNum(int sn){
        screenNum = sn;
    }
    public int[][] getPlayerView(){
        return playerView;
    }
    public double[] getViewBoxCords(){
        return viewBoxCords;
    }
    public int getPlayerViewRange(){
        return player.getViewRange();
    }
    public double getPlayerViewScale(){
        return playerViewScale;
    }
    public void movingUp(boolean up){
        keys[0] = up;
    }
    public void movingDown(boolean down){
        keys[1] = down;
    }
    public void movingRight(boolean right){
        keys[2] = right;
    }
    public void movingLeft(boolean left){
        keys[3] = left;
    }
    public int getWindowNum(){
        return screenNum;
    }
    public int[][] getWorld(){
        return world;
    }
    public double[] getPlayerCords(){
        return player.getCords();
    }
    public double getSeed(){
        return seed;
    }
    public Player getPlayer(){
        return player;
    }
}
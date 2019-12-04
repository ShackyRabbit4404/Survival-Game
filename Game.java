public class Game{
    //0 = main menu, 1 = player view, 2 = map view,
    private int screenNum;
    //0 = empty, 1 = dirt, 2 = stone, 3 = bedrock
    private int[][] world;
    private int[][] playerView;
    private double hillIntensity;
    private double caveIntensity;
    private double caveWallThickness;
    private Player player;
    //0 = up, 1 = down, 2 = right, 3 = left
    private boolean[] keys;
    public Game(int w, int h,double hi,double ci,double cwt){
        screenNum = 2;
        world = new int[1][1];
        hillIntensity = hi;
        caveIntensity = ci;
        caveWallThickness = cwt;
        generateWorld(w,h);
        //num blocks the player can see in any direction
        int playerViewRange = 20;
        //blocks per second
        double playerSpeed = 5;
        player = new Player(w/2,h/4,playerViewRange,playerSpeed);
        keys = new boolean[4];
    }
    public void generateWorld(int w, int h){
        Noise noise = new Noise();
        world = new int[w][h];
        double seed = 1000000*Math.random();
        System.out.println("Hill Intensity: "+hillIntensity+" Cave Intensity: "+caveIntensity);
        //generates world terrain based on the seed
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
                if(noise.noise(((double)x/caveIntensity)+seed,(double)y/caveIntensity) < caveWallThickness && noise.noise(((double)x/caveIntensity)+seed,(double)y/caveIntensity) > 0.03){
                    world[x][y] = 0;
                }
            }
        }
    }
    public void update(double delay){
        if(keys[0])
            player.moveVertically(delay*-1);
        if(keys[1])
            player.moveVertically(delay);
        if(keys[2])
            player.moveHorizontally(delay);
        if(keys[3])
            player.moveHorizontally(delay*-1);
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
}
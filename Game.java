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
        player = new Player(w/2,h/4,playerViewRange,playerSpeed);
        keys = new boolean[4];
        screenWidth = sw;
        screenHeight = sh;
        playerViewScale = screenWidth/((double)playerViewRange*2+1);
        int blocksUp = (int)((((double)screenHeight/playerViewScale)-1)/2.0+0.5);
        System.out.println("Player block scale: "+playerViewScale);
        playerView = new int[playerViewRange*2+2][blocksUp*2+2];
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
        if(keys[0] && player.getCords()[1] > 0)
            player.moveVertically(delay*-1);
        if(keys[1] && player.getCords()[1] < world[0].length)
            player.moveVertically(delay);
        if(keys[2] && player.getCords()[0] < world.length)
            player.moveHorizontally(delay);
        if(keys[3] && player.getCords()[0] > 0)
            player.moveHorizontally(delay*-1);
        setPlayerView();
    }
    public void setPlayerView(){
        int blockSize = (int)(screenWidth/((double)player.getViewRange()*2+1)+0.5);
        int blocksUp = (int)((((double)screenHeight/(double)blockSize)-1)/2);
        viewBoxCords = new double[]{player.getCords()[0]-player.getViewRange(),player.getCords()[1]-blocksUp};
        if(viewBoxCords[0] < 0){
            viewBoxCords[0] = 0;
        }
        else if(viewBoxCords[0] > world.length-playerView.length){
            viewBoxCords[0] = world.length-player.getViewRange()*2-1;
        }
        if(viewBoxCords[1] < 0){
            viewBoxCords[1] =  0;
        }
        else if(viewBoxCords[1] > world[0].length-playerView[0].length){
            viewBoxCords[1] = world[0].length-blocksUp*2-1;
        }
        for(int x = 0; x < playerView.length;x++){
            for(int y = 0; y < playerView[0].length;y++){
                playerView[x][y] = world[(int)(viewBoxCords[0])+x][(int)(viewBoxCords[1])+y];
            }
        }
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
}
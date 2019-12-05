public class Game{
    //0 = main menu, 1 = player view, 2 = map view,
    private int screenNum;
    //0 = empty, 1 = dirt, 2 = stone, 3 = bedrock
    private int[][] screen;
    private int[][] playerView;
    private double hillIntensity;
    private double caveIntensity;
    private double caveWallThickness;
    private double seed;
    private int screenWidth;
    private int screenHeight;
    private Player player;
    //0 = up, 1 = down, 2 = right, 3 = left
    private boolean[] keys;
    public Game(int w, int h,double hi,double ci,double cwt,int ww,int wh){
        screenNum = 2;
        screen = new int[1][1];
        hillIntensity = hi;
        caveIntensity = ci;
        caveWallThickness = cwt;
        generatescreen(w,h);
        //num blocks the player can see in any direction
        int playerViewRange = 20;
        //blocks per second
        double playerSpeed = 20;
        player = new Player(w/2,h/4,playerViewRange,playerSpeed);
        keys = new boolean[4];
        screenWidth = ww;
        screenHeight = wh;
        playerView = new int[playerViewRange*2+1][];
    }
    public void generatescreen(int w, int h){
        Noise noise = new Noise();
        screen = new int[w][h];
        seed = 1000000*Math.random();
        System.out.println("Hill Intensity: "+hillIntensity+" Cave Intensity: "+caveIntensity);
        //generates screen terrain based on the seed
        for(double x = 0; x < w; x++){
            int temp = (int)(((noise.noise((x/hillIntensity)+seed)+1)/2)*(double)(h/2));
            screen[(int)x][temp] = 1;
            for(int y = temp+1; y < screen[0].length; y++){
                if(((double)y)/((double)screen[0].length) > Math.random()/10+0.35){
                    screen[(int)x][y] = 3;
                }
                else{
                    screen[(int)x][y] = 2;
                }
            }
        }
        //generates the cave systems based off of the seed
        for(int x = 0; x < screen.length; x++){
            for(int y = 0; y < screen[0].length; y++){
                if(noise.noise(((double)x/caveIntensity)+seed,(double)y/caveIntensity) < caveWallThickness && noise.noise(((double)x/caveIntensity)+seed,(double)y/caveIntensity) > 0.03){
                    screen[x][y] = 0;
                }
            }
        }
    }
    public void update(double delay){
        if(keys[0] && player.getCords()[1] > 0)
            player.moveVertically(delay*-1);
        if(keys[1] && player.getCords()[1] < screen[0].length)
            player.moveVertically(delay);
        if(keys[2] && player.getCords()[0] < screen.length)
            player.moveHorizontally(delay);
        if(keys[3] && player.getCords()[0] > 0)
            player.moveHorizontally(delay*-1);
    }
    public void setPlayerView(){
        int blockSize = (int)(screenWidth/(double)player.getViewRange()+0.5);
        int blocksUp = ((screenHeight/blockSize)-1)/2+1;
        double[] startCords = new double[]{player.getCords()[0]-player.getViewRange(),player.getCords()[1]-blocksUp};
        if(startCords[0] < 0){
            startCords[0] = 0;
        }
        else if(startCords[0] > screen.length-player.getViewRange()*2-1){
            startCords[0] = screen.length-player.getViewRange()*2-1;
        }
        if(startCords[1] < 0){
            startCords[1] =  0;
        }
        else if(startCords[1] > screen[0].length-blocksUp*2-1){
            startCords[1] = screen[0].length-blocksUp*2-1;
        }
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
    public int[][] getscreen(){
        return screen;
    }
    public double[] getPlayerCords(){
        return player.getCords();
    }
    public double getSeed(){
        return seed;
    }
}
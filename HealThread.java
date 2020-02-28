public class HealThread implements Runnable{
    private Player player;
    private double maxHealth;
    //seconds
    private double coolDown;
    //seconds
    private double waitBetweenHeals;
    private double healthRegenPerRound;
    public HealThread(Player p, double mh){
        player = p;
        maxHealth = mh;
        coolDown = 0;
        waitBetweenHeals = 5;
        
    }
    public void run(){
        while(true){
            
        }
    }
}
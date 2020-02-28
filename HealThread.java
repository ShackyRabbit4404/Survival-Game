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
        healthRegenPerRound = maxHealth*0.05;
    }
    public void run(){
        while(player.isAlive()){
            try{
                Thread.sleep(1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            if(coolDown > 0){
                coolDown--;
            }
            else if(player.getHealth() != maxHealth){
                player.changeHealth(healthRegenPerRound);
                coolDown = waitBetweenHeals;
            }
        }
    }
}
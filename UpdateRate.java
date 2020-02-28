public class UpdateRate implements Runnable{
    private int updatesPerSecond;
    private Game game;
    public UpdateRate(Game g,int ups){
        game = g;
        updatesPerSecond = ups;
    }
    public void run(){
        while(game.getPlayer().isAlive()){
            new Thread(new Update(game,1.0/(double)updatesPerSecond)).start();
            try{
                Thread.sleep(1000/updatesPerSecond);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
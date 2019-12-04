public class UpdateRate implements Runnable{
    private int updatesPerSecond;
    private Game game;
    public UpdateRate(Game g,int ups){
        game = g;
        updatesPerSecond = ups;
    }
    public void run(){
        new Thread(new Update(game,1.0/(double)updatesPerSecond)).start();
        while(true){
            try{
                Thread.sleep(1000/updatesPerSecond);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
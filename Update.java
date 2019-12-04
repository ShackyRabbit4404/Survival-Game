public class Update implements Runnable{
    private Game game;
    private double updateTime;
    public Update(Game g,double ut){
        game = g;
        updateTime = ut;
    }
    public void run(){
        if(game!=null){
            game.update(updateTime);
        }
    }
}
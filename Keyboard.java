import java.awt.event.*;
public class Keyboard extends KeyAdapter{
    private Game game;
    public Keyboard(Game g){
        game = g;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W){
            game.movingUp(true);
        }
        if(key == KeyEvent.VK_S){
            game.movingDown(true);
        }
        if(key == KeyEvent.VK_D){
            game.movingRight(true);
        }
        if(key == KeyEvent.VK_A){
            game.movingLeft(true);
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W){
            game.movingUp(false);
        }
        if(key == KeyEvent.VK_S){
            game.movingDown(false);
        }
        if(key == KeyEvent.VK_D){
            game.movingRight(false);
        }
        if(key == KeyEvent.VK_A){
            game.movingLeft(false);
        }
    }
}
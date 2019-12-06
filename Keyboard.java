import java.awt.event.*;
public class Keyboard extends KeyAdapter{
    private Game game;
    public Keyboard(Game g){
        game = g;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
            game.movingUp(true);
        }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
            game.movingDown(true);
        }
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            game.movingRight(true);
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            game.movingLeft(true);
        }
        if(key == KeyEvent.VK_1){
            game.setScreenNum(1);
        }
        if(key == KeyEvent.VK_2){
            game.setScreenNum(2);
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
            game.movingUp(false);
        }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
            game.movingDown(false);
        }
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            game.movingRight(false);
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            game.movingLeft(false);
        }
    }
}
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
        if(key == KeyEvent.VK_V){
            game.setScreenNum(1);
        }
        if(key == KeyEvent.VK_M){
            game.setScreenNum(2);
        }
        if(key == KeyEvent.VK_1){
            game.setPlayerHotbarItemSelected(0);
        }
        if(key == KeyEvent.VK_2){
            game.setPlayerHotbarItemSelected(1);
        }
        if(key == KeyEvent.VK_3){
            game.setPlayerHotbarItemSelected(2);
        }
        if(key == KeyEvent.VK_4){
            game.setPlayerHotbarItemSelected(3);
        }
        if(key == KeyEvent.VK_5){
            game.setPlayerHotbarItemSelected(4);
        }
        if(key == KeyEvent.VK_6){
            game.setPlayerHotbarItemSelected(5);
        }
        if(key == KeyEvent.VK_7){
            game.setPlayerHotbarItemSelected(6);
        }
        if(key == KeyEvent.VK_8){
            game.setPlayerHotbarItemSelected(7);
        }
        if(key == KeyEvent.VK_9){
            game.setPlayerHotbarItemSelected(8);
        }
        if(key == KeyEvent.VK_0){
            game.setPlayerHotbarItemSelected(9);
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
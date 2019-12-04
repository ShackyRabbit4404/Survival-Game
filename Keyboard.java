import java.awt.event.*;
public class Keyboard extends KeyAdapter{
    private Game game;
    public Keyboard(Game g){
        game = g;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W){
            
        }
    }
    public void keyReleased(KeyEvent e){
        
    }
}
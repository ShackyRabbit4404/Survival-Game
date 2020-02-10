import java.awt.event.*;
import java.awt.MouseInfo;
public class Mouse implements MouseListener{
    private Game game;
    private Display display;
    public Mouse(Game g, Display d){
        game = g;
        display = d;
    }
    public void mousePressed(MouseEvent e){
        game.clicked((int)(MouseInfo.getPointerInfo().getLocation().getX()),(int)(MouseInfo.getPointerInfo().getLocation().getY()),1);
    }
    public void mouseReleased(MouseEvent e){
        
    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseClicked(MouseEvent e) {
        
    }
}
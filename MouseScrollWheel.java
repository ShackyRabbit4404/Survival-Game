import java.awt.event.*;
import java.awt.*;
public class MouseScrollWheel implements MouseWheelListener{
    Game game;
    public MouseScrollWheel(Game g){
        game = g;
    }
    public void mouseWheelMoved(MouseWheelEvent e){
        int itemSelected = game.getPlayer().getHotbarItemSelected();
        itemSelected += e.getWheelRotation();
        if(itemSelected <0){
            itemSelected = game.getPlayer().getHotbar().length-1;
        }
        itemSelected = itemSelected%game.getPlayer().getHotbar().length;
        game.setPlayerHotbarItemSelected(itemSelected);
    }
}
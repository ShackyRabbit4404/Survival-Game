import java.awt.*;
import javax.swing.*;
public class Display extends JComponent{
    private int width;
    private int height;
    private Game game;
    private int[][] world;
    private int scale;
    public Display(int w, int h, Game g,int s){
        super();
        width = w;
        height = h;
        System.out.println("Width: "+width+" Height: "+height);
        game = g;
        world = game.getWorld();
        scale = s;
    }
    public void draw(){
        super.repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.CYAN);
        g.fillRect(0,0,width*scale,height*scale);
        if(game.getWindowNum() == 2){
            for(int x = 0; x < world.length*scale; x+=scale){
                for(int y = 0; y < world[0].length*scale; y+= scale){
                    if(world[x/scale][y/scale] != 0){
                        if(world[x/scale][y/scale] == 1){
                            g.setColor(Color.GREEN);
                        }
                        else if(world[x/scale][y/scale] == 2){
                            g.setColor(new Color(102,66,0));
                        }
                        else if(world[x/scale][y/scale] == 3){
                            g.setColor(new Color(105,105,105));
                        }
                        g.fillRect(x,y,scale,scale);
                    }
                }
            }
        }
        else if(game.getWindowNum() == 1){
            
        }
    }
}
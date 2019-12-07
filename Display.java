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
            g.setColor(Color.RED);
            g.fillOval((int)(game.getPlayerCords()[0]*(double)scale),(int)(game.getPlayerCords()[1]*(double)scale),scale,scale);
            g.setColor(Color.BLACK);
            g.drawRect((int)(game.getViewBoxCords()[0]*scale),(int)(game.getViewBoxCords()[1]*scale),(int)(game.getPlayerView().length*scale),(int)(game.getPlayerView()[0].length*scale));
            g.drawString("X: "+game.getPlayerCords()[0]+" Y: "+game.getPlayerCords()[1],20,20);
            g.drawString("Seed: "+game.getSeed(),20,40);
            g.drawString("Grounded: "+game.getPlayer().getGrounded(),20,60);
            g.drawString("Vertical Velocity: "+game.getPlayer().getVertVelocity(),20,80);
        }
        else if(game.getWindowNum() == 1){
            int[][] viewPlane = game.getPlayerView();
            double playerViewScale = game.getPlayerViewScale();
            double[] viewBoxCords = game.getViewBoxCords();
            for(int x = 0; x < viewPlane.length;x++){
                for(int y = 0; y < viewPlane[0].length;y++){
                    if(viewPlane[x][y] != 0){
                        if(viewPlane[x][y] == 1){
                            g.setColor(Color.GREEN);
                        }
                        else if(viewPlane[x][y] == 2){
                            g.setColor(new Color(102,66,0));
                        }
                        else if(viewPlane[x][y] == 3){
                            g.setColor(new Color(105,105,105));
                        }
                        g.fillRect((int)(((double)x-(viewBoxCords[0]%1))*playerViewScale),(int)(((double)y-(viewBoxCords[1]%1))*playerViewScale),(int)(playerViewScale+0.5),(int)(playerViewScale+0.5));
                    }
                }
            }
            g.setColor(Color.RED);
            g.fillRect((int)((game.getPlayerCords()[0]-viewBoxCords[0])*playerViewScale),(int)((game.getPlayerCords()[1]-viewBoxCords[1])*playerViewScale),(int)playerViewScale,(int)playerViewScale);
            g.setColor(Color.BLACK);
            g.drawString("X: "+game.getPlayerCords()[0]+" Y: "+game.getPlayerCords()[1],20,20);
            g.drawString("Seed: "+game.getSeed(),20,40);
            
        }
    }
}
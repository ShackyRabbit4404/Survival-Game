import java.awt.*;
import javax.swing.*;
import java.util.*;
public class Display extends JComponent{
    private int width;
    private int height;
    private Game game;
    private int[][] world;
    private int scale;
    private ArrayList<Image> textures;
    private double screenWidth;
    private double screenHeight;
    private Font font1 = new Font("SansSerif", Font.BOLD, 25);
    private Font font2 = new Font("SansSerif", Font.PLAIN, 10);
    //constructor method
    public Display(int w, int h, Game g,int s,double sw, double sh){
        super();
        width = w;
        height = h;
        System.out.println("Width: "+width+" Height: "+height);
        game = g;
        world = game.getWorld();
        scale = s;
        textures = new ArrayList<Image>();
        scaleTextures(game.getTextures());
        screenWidth = sw;
        screenHeight = sh;
    }
    
    public void scaleTextures(ArrayList<Image> tempT){
        double s = game.getPlayerViewScale();
        System.out.println("Textures scaled to "+s+" x "+s);
        for(Image i : tempT){
            textures.add(i.getScaledInstance((int)s+1,(int)s+1,0));
        }
    }
    //calls the draw method
    public void draw(){
        super.repaint();
    }
    //draws the graphcs on the screen
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width*scale,height*scale);
        if(game.getWindowNum() == 2){
            for(int x = 0; x < world.length*scale; x+=scale){
                for(int y = 0; y < world[0].length*scale; y+= scale){
                    if(game.getDiscoverdWorld()[x/scale][y/scale] == 1){
                        if(world[x/scale][y/scale] == 0){
                            g.setColor(Color.CYAN);
                        }
                        else if(world[x/scale][y/scale] == 1){
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
            g.fillRect((int)(game.getPlayerCords()[0]*(double)scale),(int)(game.getPlayerCords()[1]*(double)scale),scale*game.getPlayerDimentions()[0],scale*game.getPlayerDimentions()[1]);
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
            g.setColor(Color.CYAN);
            for(int x = 0; x < viewPlane.length;x++){
                for(int y = 0; y < viewPlane[0].length;y++){
                    if(viewPlane[x][y] != 0 && game.getDiscoverdWorld()[x+(int)viewBoxCords[0]][y+(int)viewBoxCords[1]] == 1){
                        g.drawImage(textures.get(viewPlane[x][y]-1),(int)(((double)x-(viewBoxCords[0]%1))*playerViewScale),(int)(((double)y-(viewBoxCords[1]%1))*playerViewScale),this);
                        /*
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
                        */
                    }
                    else if(viewPlane[x][y] == 0 && game.getDiscoverdWorld()[x+(int)viewBoxCords[0]][y+(int)viewBoxCords[1]] == 1){
                        g.fillRect((int)(((double)x-(viewBoxCords[0]%1))*playerViewScale),(int)(((double)y-(viewBoxCords[1]%1))*playerViewScale),(int)(playerViewScale+1),(int)(playerViewScale+1));
                    }
                }
            }
            g.setColor(Color.RED);
            g.fillRect((int)((game.getPlayerCords()[0]-viewBoxCords[0])*playerViewScale),(int)((game.getPlayerCords()[1]-viewBoxCords[1])*playerViewScale),(int)playerViewScale*game.getPlayerDimentions()[0],(int)playerViewScale*game.getPlayerDimentions()[1]);
            g.setColor(Color.BLACK);
            g.setFont(font2);
            g.drawString("X: "+game.getPlayerCords()[0]+" Y: "+game.getPlayerCords()[1],20,20);
            g.drawString("Seed: "+game.getSeed(),20,40);
            g.drawString("Grounded: "+game.getPlayer().getGrounded(),20,60);
            g.drawString("Vertical Velocity: "+game.getPlayer().getVertVelocity(),20,80);
            g.drawString("View Box Cords: X:"+viewBoxCords[0]+" Y: "+viewBoxCords[1],20,100);
            int a = 0;
            for(Item i: game.getPlayerInventory()){
                if(i != null){
                    g.drawString(i.getName()+" Count: "+i.getCount(),20,120+a*20);
                    a++;
                }
            }   
            g.setColor(new Color(100,100,100,95));
            g.fillRect((int)(0.09*viewPlane.length*playerViewScale),(int)(0.8*viewPlane[0].length*playerViewScale),(int)(0.82*viewPlane.length*playerViewScale),(int)(0.12*viewPlane[0].length*playerViewScale));
            g.setColor(Color.WHITE);
            for(int i = 0; i < game.getPlayerHotbar().length;i++){
                if(i == game.getPlayer().getHotbarItemSelected()){
                    g.setColor(Color.BLACK);
                    g.fillRect((int)(0.11*viewPlane.length*playerViewScale)+(int)(i*0.08*viewPlane.length*playerViewScale),(int)(0.81*viewPlane[0].length*playerViewScale),(int)(0.06*playerViewScale*viewPlane.length),(int)(0.1*viewPlane[0].length*playerViewScale));
                    g.setColor(Color.WHITE);
                }
                else{
                    g.fillRect((int)(0.11*viewPlane.length*playerViewScale)+(int)(i*0.08*viewPlane.length*playerViewScale),(int)(0.81*viewPlane[0].length*playerViewScale),(int)(0.06*playerViewScale*viewPlane.length),(int)(0.1*viewPlane[0].length*playerViewScale));
                }
                if(game.getPlayer().getHotbar()[i] != null){
                    g.drawImage(textures.get(game.getPlayer().getHotbar()[i].getTextureNum()),(int)(0.125*viewPlane.length*playerViewScale)+(int)(i*0.08*viewPlane.length*playerViewScale),(int)(0.83*viewPlane[0].length*playerViewScale),this);
                }
            }
            if(game.isInvenVisible()){
                int rowLength = 10;
                g.setColor(new Color(100,100,100,180));
                g.fillRect((int)(0.05*viewPlane.length*playerViewScale),(int)(0.06*viewPlane[0].length*playerViewScale),(int)(0.9*viewPlane.length*playerViewScale),(int)(0.7*viewPlane[0].length*playerViewScale));
                g.setColor(Color.WHITE);
                int count = 0; 
                for(Item i: game.getPlayerInventory()){
                    if(i!=null){
                        g.drawImage(textures.get(i.getTextureNum()),(int)(0.07*viewPlane.length*playerViewScale)+(int)((count%rowLength)*0.04*viewPlane.length*playerViewScale),(int)(0.07*viewPlane.length*playerViewScale)+(int)((int)(count/rowLength)*0.03*viewPlane.length*playerViewScale),this);
                        g.drawString("x"+i.getCount(),(int)(0.1*viewPlane.length*playerViewScale)+(int)((count%rowLength)*0.04*viewPlane.length*playerViewScale),(int)(0.085*viewPlane.length*playerViewScale)+(int)((int)(count/rowLength)*0.03*viewPlane.length*playerViewScale));
                        count++;
                    }
                }
            }
            else if(game.isCraftingVisible()){
                g.setColor(new Color(100,100,100,180));
                g.fillRect((int)(0.05*viewPlane.length*playerViewScale),(int)(0.05*viewPlane[0].length*playerViewScale),(int)(0.9*viewPlane.length*playerViewScale),(int)(0.7*viewPlane[0].length*playerViewScale));
            }
            g.setColor(Color.BLACK);
            g.drawRect((int)(screenWidth*0.8),(int)(screenHeight*.03),(int)(screenWidth*.18),(int)(screenHeight*0.04));
            g.setColor(new Color((int)(255*(1-game.getPlayerHealthPercent())),(int)(255*game.getPlayerHealthPercent()),0));
            g.fillRect((int)(screenWidth*0.8)+1,(int)(screenHeight*.03)+1,(int)(screenWidth*.18*game.getPlayerHealthPercent())-1,(int)(screenHeight*0.04)-1);
            g.setColor(new Color(50,50,50,200));
            g.setFont(font1);
            g.drawString(game.getPlayerHealthPercent()*100+"%",(int)(screenWidth*.88),(int)(screenHeight*0.06));
        }
    }
    //returns the scale on the map view
    public int getScale(){
        return scale;
    }
}
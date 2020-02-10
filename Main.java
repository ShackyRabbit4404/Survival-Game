import javax.swing.*;
import java.awt.*;
public class Main{
    public static void main(String[] args){
        //----------------------Setting up varaibles-----------------
        JFrame frame = new JFrame("Survival Game");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Stores all game info and methods
        int screenScale = 5;
        int hillIntensity = 50;
        //40
        int caveIntensity = 40;
        //0.15
        double caveWallThickness = .15;
        int worldWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/screenScale); 
        int worldHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/screenScale);
        Game game = new Game(worldWidth,worldHeight,hillIntensity,caveIntensity,caveWallThickness,(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(),screenScale);
        //Display class draws the graphics
        Display screen = new Display(worldWidth,worldWidth,game,screenScale);
        frame.add(screen);
        //number of times per second
        int frameRate = 30;
        int updateRate = 30;
        //Creates the user input classes
        Keyboard keyboard = new Keyboard(game);
        Mouse mouse = new Mouse(game,screen);
        MouseScrollWheel mouseWheel = new MouseScrollWheel(game);
        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.addMouseWheelListener(mouseWheel);
        //Starts the game 
        new Thread(new FrameThread(screen,frameRate)).start();
        new Thread(new UpdateRate(game,updateRate)).start();
        frame.setVisible(true);
    }
}   
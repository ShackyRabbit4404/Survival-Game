public class FrameThread implements Runnable{
    private Display frame;
    private int frameRate;
    public FrameThread(Display f,int fr){
        frame = f;
        frameRate = fr;
    }   
    public void run(){
        while(true){
            new Thread(new DisplayThread(frame)).start();
            try{
                Thread.sleep(1000/frameRate);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }   
    }
}